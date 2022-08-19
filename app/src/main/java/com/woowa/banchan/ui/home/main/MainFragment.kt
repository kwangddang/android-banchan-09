package com.woowa.banchan.ui.home.main

import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentMainBinding
import com.woowa.banchan.ui.common.uistate.UiState
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
        MainRVAdapter(checkedChangeListener, spinnerCallback, itemClickListener, cartClickListener)
    }

    private val spinnerCallback: (Int) -> Unit = { position ->
        viewModel.sortList(position)
    }

    private val checkedChangeListener: (RadioGroup, Int) -> Unit = { group, checkedId ->
        when (checkedId) {
            R.id.btn_grid -> {
                mainAdapter.managerType = GRID
            }
            R.id.btn_linear -> {
                mainAdapter.managerType = LINEAR_VERTICAL
            }
        }
        mainAdapter.homeRVAdapter.managerType = mainAdapter.managerType
        mainAdapter.notifyItemChanged(2)
    }

    override fun initAdapter() {
        binding.rvMain.adapter = mainAdapter
    }

    override fun initViews() {
        viewModel.getFoods(getString(R.string.fragment_main))
    }

    override fun initObserve() {
        viewModel.itemUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    mainAdapter.submitHeaderList(state.data)
                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}