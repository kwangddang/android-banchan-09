package com.woowa.banchan.ui.cart.cart

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
import androidx.work.WorkManager
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.CartViewModel
import com.woowa.banchan.ui.cart.cart.adapter.CartRVAdapter
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.detail.DetailActivity
import com.woowa.banchan.ui.order.detail.OrderDetailActivity
import com.woowa.banchan.utils.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by activityViewModels()

    private val cartRVAdapter: CartRVAdapter by lazy {
        CartRVAdapter()
    }

    private val workManager: WorkManager by lazy {
        WorkManager.getInstance(requireContext())
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

    override fun onStop() {
        viewModel.updateCart()
        super.onStop()
    }

    private fun initListener() {
        val listener = object : CartRVAdapter.CartButtonCallBackListener {
            override fun onClickCartUpdate(cart: Cart, message: String?) {
                message?.let { showToast(it) }
                viewModel.addUpdateCartCache(cart, removeFlag = false)
            }

            override fun onClickCartRemove(cart: Cart) {
                viewModel.addUpdateCartCache(cart, removeFlag = true)
            }

            override fun onClickOrderButton() {
                viewModel.addOrder()
            }

            override fun onClickRecentItem(recent: Recent) {
                val intent =
                    Intent(this@CartFragment.requireActivity(), DetailActivity::class.java)
                with(intent) {
                    putExtra("title", recent.title)
                    putExtra("hash", recent.hash)
                }
                startActivity(intent)
                this@CartFragment.requireActivity().finish()
            }

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
                    is UiState.Success -> cartRVAdapter.submitCartList(it.data.values.toList())
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.recentUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> cartRVAdapter.setPreviewList(it.data)
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.orderUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
                        viewModel.reserveUpdateOrder(it.data,workManager)
                        intent.putExtra("order", it.data)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    is UiState.Error -> showToast(it.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter() {
        binding.rvCartContent.adapter = cartRVAdapter
    }

    companion object {
        const val freeShipping = 40000
        const val shipping = 2500
        const val minimumPrice = 10000

        const val minimumCount = 1
        const val maximumCount = 100
    }
}