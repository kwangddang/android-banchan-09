package com.woowa.banchan.ui.home.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentMainBinding
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.detail.DetailActivity
import com.woowa.banchan.ui.home.GRID
import com.woowa.banchan.ui.home.LINEAR_VERTICAL
import com.woowa.banchan.ui.home.main.adapter.MainRVAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val mainAdapter: MainRVAdapter by lazy {
        MainRVAdapter(checkedChangeListener, spinnerCallback, itemClickListener)
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

    private val itemClickListener: (String, String) -> Unit = { title, hash ->
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("hash", hash)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViews()
        initObserve()
    }

    private fun initAdapter() {
        binding.rvMain.adapter = mainAdapter
    }

    private fun initViews() {
        viewModel.getMainFoods()
    }

    private fun initObserve() {
        viewModel.mainUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    mainAdapter.submitHeaderList(state.data)
                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}