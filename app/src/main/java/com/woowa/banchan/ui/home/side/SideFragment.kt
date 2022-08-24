package com.woowa.banchan.ui.home.side

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentSideBinding
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.common.viewutils.showContent
import com.woowa.banchan.ui.common.viewutils.showError
import com.woowa.banchan.ui.common.viewutils.showLoading
import com.woowa.banchan.ui.home.HomeBaseFragment
import com.woowa.banchan.ui.home.adapter.soupside.SoupSideRVAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SideFragment : HomeBaseFragment<FragmentSideBinding>(R.layout.fragment_side) {

    override val viewModel: SideViewModel by viewModels()

    private val soupSideAdapter: SoupSideRVAdapter by lazy {
        SoupSideRVAdapter(false, viewModel.spinnerPosition, spinnerCallback, homeRVAdapter)
    }

    private val spinnerCallback: (Int) -> Unit = { position ->
        viewModel.spinnerPosition = position
        viewModel.sortList()
    }

    override fun initAdapter() {
        binding.rvSide.adapter = soupSideAdapter
    }

    override fun initViews() {
        viewModel.getFoods(getString(R.string.fragment_side))
    }

    override fun initObserve() {
        viewModel.itemUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        if (homeRVAdapter.itemCount == 0)
                            soupSideAdapter.submitHeaderList(state.data)
                        homeRVAdapter.submitList(state.data)
                        showContent(binding.rvSide, binding.pbLoading, binding.evNetwork)
                    }
                    is UiState.Error -> {
                        showError(binding.rvSide, binding.pbLoading, binding.evNetwork)
                        showToast(state.message)
                    }
                    is UiState.Loading -> showLoading(
                        binding.rvSide,
                        binding.pbLoading,
                        binding.evNetwork
                    )
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}