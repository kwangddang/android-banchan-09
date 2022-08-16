package com.woowa.banchan.ui.home.best

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentBestBinding
import com.woowa.banchan.ui.common.bottomsheet.CartAddFragment
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.detail.DetailActivity
import com.woowa.banchan.ui.home.best.adapter.BestRVAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BestFragment : Fragment() {

    private lateinit var binding: FragmentBestBinding

    private val viewModel: BestViewModel by viewModels()

    private val bestAdapter: BestRVAdapter by lazy {
        BestRVAdapter(itemClickListener, cartClickListener)
    }

    private val itemClickListener: (String, String) -> Unit = { title, hash ->
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("hash", hash)
        startActivity(intent)
    }

    private val cartClickListener: () -> Unit = {
        CartAddFragment().show(childFragmentManager, "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_best, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViews()
        initObserve()
    }

    private fun initAdapter() {
        binding.rvBest.adapter = bestAdapter
    }

    private fun initViews() {
        viewModel.getBestFoods()
    }

    private fun initObserve() {
        viewModel.bestUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    bestAdapter.submitHeaderList(state.data)
                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}