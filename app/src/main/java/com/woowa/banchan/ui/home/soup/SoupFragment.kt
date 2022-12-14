package com.woowa.banchan.ui.home.soup

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentSoupBinding
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
class SoupFragment : HomeBaseFragment<FragmentSoupBinding>(R.layout.fragment_soup) {

    override val viewModel: SoupViewModel by viewModels()

    private val soupSideAdapter: SoupSideRVAdapter by lazy {
        SoupSideRVAdapter(
            true,
            viewModel.spinnerPosition,
            viewModel.spinnerCallback,
            homeRVAdapter
        )
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun initAdapter() {
        binding.rvSoup.adapter = soupSideAdapter
    }

    override fun initViews() {
        viewModel.type = getString(R.string.fragment_soup)
        viewModel.getFoods(viewModel.type!!)
    }

    override fun initObserver() {
        viewModel.itemUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        if (homeRVAdapter.itemCount == 0)
                            soupSideAdapter.submitHeaderList(state.data)
                        homeRVAdapter.submitList(state.data)
                        showContent(binding.rvSoup, binding.pbLoading, binding.evNetwork)
                    }
                    is UiState.Error -> {
                        showError(binding.rvSoup, binding.pbLoading, binding.evNetwork)
                        showToast(state.error.message)
                    }
                    is UiState.Loading -> showLoading(
                        binding.rvSoup,
                        binding.pbLoading,
                        binding.evNetwork
                    )
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}