package com.woowa.banchan.ui.home.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentMainBinding
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.common.viewutils.showContent
import com.woowa.banchan.ui.common.viewutils.showLoading
import com.woowa.banchan.ui.home.GRID
import com.woowa.banchan.ui.home.HomeBaseFragment
import com.woowa.banchan.ui.home.LINEAR_VERTICAL
import com.woowa.banchan.ui.home.main.adapter.MainRVAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : HomeBaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()

    private val mainAdapter: MainRVAdapter by lazy {
        MainRVAdapter(viewModel.checkedChangeListener, viewModel.spinnerPosition, viewModel.spinnerCallback, homeRVAdapter)
    }

    override fun initAdapter() {
        binding.rvMain.adapter = mainAdapter
    }

    override fun initViews() {
        viewModel.getFoods(getString(R.string.fragment_main))
    }

    override fun initObserver() {
        viewModel.itemUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        homeRVAdapter.submitList(state.data)
                        showContent(binding.rvMain, binding.pbLoading)
                    }
                    is UiState.Error -> {
                        showContent(binding.rvMain, binding.pbLoading)
                        showToast(state.message)
                    }
                    is UiState.Loading -> showLoading(binding.rvMain, binding.pbLoading)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.linearGridClickEvent.observe(viewLifecycleOwner, EventObserver { checkedId ->
            when (checkedId) {
                R.id.btn_grid -> {
                    mainAdapter.managerType = GRID
                }
                R.id.btn_linear -> {
                    mainAdapter.managerType = LINEAR_VERTICAL
                }
            }
            homeRVAdapter.managerType = mainAdapter.managerType
            mainAdapter.notifyItemChanged(2)
        })
    }

}