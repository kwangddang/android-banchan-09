package com.woowa.banchan.ui.cart.recent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentRecentBinding
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.CartViewModel
import com.woowa.banchan.ui.cart.recent.adapter.RecentRVAdapter
import com.woowa.banchan.ui.common.bottomsheet.CartAddFragment
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.detail.DetailActivity
import com.woowa.banchan.utils.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecentFragment : Fragment() {

    lateinit var binding: FragmentRecentBinding

    private val viewModel: CartViewModel by activityViewModels()

    private val adapter: RecentRVAdapter by lazy {
        RecentRVAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recent, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListener()
        initObserver()
    }

    private fun initAdapter() {
        binding.rvRecentlyViewed.adapter = adapter
    }

    private fun initListener() {
        val listener = object : RecentRVAdapter.RecentlyViewedCallBackListener {
            override fun onClickItem(recent: Recent) {
                viewModel.recentClickListener(recent)
            }

            override fun onClickCheckButton(recent: Recent) {
                viewModel.cartDeleteListener(recent)
            }

            override fun onClickCartButton(recent: Recent) {
                viewModel.cartAddListener(recent)
            }
        }
        adapter.setRecentlyViewedCallBackListener(listener)
    }

    private fun initObserver() {
        viewModel.recentUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> adapter.submitList(it.data)
                    is UiState.Error -> showToast(it.error.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.recentClickEvent.observe(viewLifecycleOwner, EventObserver { recent ->
            val intent = Intent(this@RecentFragment.requireActivity(), DetailActivity::class.java)
            with(intent) {
                putExtra("title", recent.title)
                putExtra("hash", recent.hash)
            }
            startActivity(intent)
        })

        viewModel.bottomsheetEvent.observe(viewLifecycleOwner, EventObserver { recent ->
            CartAddFragment(recent.toFoodItem()).show(
                childFragmentManager,
                getString(R.string.fragment_cart_add)
            )
        })
    }
}