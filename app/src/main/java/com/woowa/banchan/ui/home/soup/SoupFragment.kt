package com.woowa.banchan.ui.home.soup

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentSoupBinding
import com.woowa.banchan.ui.common.uistate.UiState
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
        SoupSideRVAdapter(false, viewModel.spinnerPosition, spinnerCallback, homeRVAdapter)
    }

    private val spinnerCallback: (Int) -> Unit = { position ->
        viewModel.spinnerPosition = position
        viewModel.sortList()
    }

    override fun initAdapter() {
        binding.rvSoup.adapter = soupSideAdapter
    }

    override fun initViews() {
        viewModel.getFoods(getString(R.string.fragment_soup))
    }

    override fun initObserve() {
        viewModel.itemUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    if (homeRVAdapter.itemCount == 0)
                        soupSideAdapter.submitHeaderList(state.data)
                    homeRVAdapter.submitList(state.data)
                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}