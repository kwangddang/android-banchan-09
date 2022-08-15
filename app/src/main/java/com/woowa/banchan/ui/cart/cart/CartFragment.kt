package com.woowa.banchan.ui.cart.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.ui.cart.CartViewModel
import com.woowa.banchan.ui.cart.cart.adapter.CartRVAdapter
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.utils.showToast
import kotlinx.coroutines.flow.onEach

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by activityViewModels()

    private val cartRVAdapter: CartRVAdapter by lazy {
        CartRVAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initObserve()
        initListener()
    }

    private fun initListener() {
        val listener = object : CartRVAdapter.CartButtonCallBackListener {
            override fun onClickRemoveSelection() {}

            override fun onClickReleaseSelection() {}

            override fun onClickCartCheckState(cart: Cart) {}

            override fun onClickCartUpdateCount(cart: Cart, count: Int) {}

            override fun onClickCartRemove(cart: Cart) {}

            override fun onClickOrderButton() {}

            override fun onClickAllRecentlyViewed() {
                viewModel.setFragmentTag(getString(R.string.fragment_recent))
            }
        }
        cartRVAdapter.setCartButtonCallBackListener(listener)
    }

    private fun initObserve() {
        viewModel.cartUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> cartRVAdapter.submitCartList(it.data)
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }
        viewModel.recentUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> cartRVAdapter.setPreviewList(it.data)
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }
    }

    private fun initAdapter() {
        binding.rvCartContent.adapter = cartRVAdapter
        cartRVAdapter.submitCartList(emptyList())
    }

    companion object {
        const val freeShipping = 40000
        const val shipping = 2500
        const val minimumPrice = 10000
    }
}