package com.woowa.banchan.ui.cart.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.ui.cart.CartViewModel
import com.woowa.banchan.ui.cart.recent.adapter.RecentRVAdapter
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.utils.showToast
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
        rvRecent = requireActivity().findViewById(R.id.rv_recent_preview)
        return inflater.inflate(R.layout.fragment_recent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserve()
    }

    private fun initObserve() {
        viewModel.recentUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> adapter.setPreviewList(it.data)
                    is UiState.Error -> showToast(null)
                    else -> {}
                }
            }
    }

    private fun initAdapter() {
        rvRecent.adapter = adapter
    }
}