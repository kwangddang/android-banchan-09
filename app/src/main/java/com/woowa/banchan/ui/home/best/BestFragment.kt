package com.woowa.banchan.ui.home.best

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentBestBinding
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.home.HomeBaseFragment
import com.woowa.banchan.ui.home.best.adapter.BestRVAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BestFragment : HomeBaseFragment<FragmentBestBinding>(R.layout.fragment_best) {

    override val viewModel: BestViewModel by viewModels()

    private val bestAdapter: BestRVAdapter by lazy {
        BestRVAdapter(viewModel.itemClickListener, viewModel.cartClickListener)
    }

    override fun initAdapter() {
        binding.rvBest.adapter = bestAdapter
    }

    override fun initViews() {
        viewModel.getBestFoods()
    }

    override fun initObserver() {
        viewModel.bestUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    bestAdapter.submitHeaderList(state.data)
                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.deleteUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {

                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}