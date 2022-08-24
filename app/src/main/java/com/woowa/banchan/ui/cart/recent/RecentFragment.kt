package com.woowa.banchan.ui.cart.recent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.data.local.entity.toFoodItem
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.CartViewModel
import com.woowa.banchan.ui.cart.recent.adapter.RecentRVAdapter
import com.woowa.banchan.ui.common.bottomsheet.CartAddFragment
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.detail.DetailActivity
import com.woowa.banchan.utils.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecentFragment : Fragment() {

    private val viewModel: CartViewModel by activityViewModels()
    lateinit var rvRecent: RecyclerView
    private val adapter: RecentRVAdapter by lazy {
        RecentRVAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recent, container, false)
        rvRecent = view.findViewById(R.id.rv_recently_viewed)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListener()
        initObserver()
    }

    private fun initListener() {
        val listener = object : RecentRVAdapter.RecentlyViewedCallBackListener {

            override fun onClickItem(recent: Recent) {
                val intent =
                    Intent(this@RecentFragment.requireActivity(), DetailActivity::class.java)
                with(intent) {
                    putExtra("title", recent.title)
                    putExtra("hash", recent.hash)
                }
                startActivity(intent)
                this@RecentFragment.requireActivity().finish()
            }

            override fun onClickCheckButton(recent: Recent) {
                viewModel.deleteCart(recent)
            }

            override fun onClickCartButton(recent: Recent) {
                CartAddFragment(recent.toFoodItem()).show(
                    childFragmentManager,
                    getString(R.string.fragment_cart_add)
                )
            }
        }
        adapter.setRecentlyViewedCallBackListener(listener)
    }

    private fun initObserver() {
        viewModel.recentUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> adapter.setPreviewList(it.data)
                    is UiState.Error -> showToast(null)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter() {
        rvRecent.adapter = adapter
    }
}