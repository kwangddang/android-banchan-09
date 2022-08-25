package com.woowa.banchan.ui.cart.cart

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.ui.cart.CartViewModel
import com.woowa.banchan.ui.cart.cart.adapter.CartRVAdapter
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.key.ORDER_ID
import com.woowa.banchan.ui.common.receiver.OrderReceiver
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
        initObserver()
        initListener()
    }

    override fun onStop() {
        viewModel.updateCart()
        super.onStop()
    }

    private fun initListener() {
        val listener = object : CartRVAdapter.CartButtonCallBackListener {

            override fun onClickCartRemove(vararg cart: Cart) {
                viewModel.cartRemoveListener(cart)
            }

            override fun onClickOrderButton() {
                viewModel.orderClickListener()
            }

            override fun onClickRecentItem(recent: Recent) {
                viewModel.recentClickListener(recent)
            }

            override fun onClickAllRecentlyViewed() {
                viewModel.recentAllClickListener()
            }

            override fun onClickCartCountChange(hash: String, count: Int) {
                viewModel.cartCountChangeListener(hash, count)
            }

            override fun onClickCartStateChange(hash: String, checkState: Boolean) {
                viewModel.cartStateChangeListener(hash, checkState)
            }

            override fun onClickCartStateAllChange(cartList: List<Cart>) {
                viewModel.cartStateAllChangeListener(cartList)
            }
        }
        cartRVAdapter.setCartButtonCallBackListener(listener)
    }

    private fun initObserver() {
        viewModel.cartUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        if (viewModel.cartCache.isEmpty()) {
                            viewModel.cartCache = it.data.toMutableMap()
                            cartRVAdapter.submitCartList(it.data.values.toList())
                        } else {
                            cartRVAdapter.submitCartList(viewModel.cartCache.values.toList())
                        }
                    }
                    is UiState.Error -> showToast(it.error.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.deletionUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                if (state is UiState.Error)
                    showToast(state.error.message)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.recentUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> cartRVAdapter.setPreviewList(it.data)
                    is UiState.Error -> showToast(it.error.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.insertionUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
                        setAlarm(state.data, viewModel.orderTitle)
                        intent.putExtra(ORDER_ID, state.data)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    is UiState.Error -> showToast(state.error.message)
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.messageEvent.observe(viewLifecycleOwner, EventObserver { showToast(it) })

        viewModel.recentClickEvent.observe(viewLifecycleOwner, EventObserver { recent ->
            val intent = Intent(this@CartFragment.requireActivity(), DetailActivity::class.java)
            with(intent) {
                putExtra("title", recent.title)
                putExtra("hash", recent.hash)
            }
            startActivity(intent)
        })

    }

    private fun initAdapter() {
        binding.rvCartContent.adapter = cartRVAdapter
    }

    private fun setAlarm(orderId: Long, orderTitle: String) {
        val alarmManager: AlarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmIntent: PendingIntent =
            Intent(requireContext(), OrderReceiver::class.java).let { intent ->
                intent.putExtra(getString(R.string.order_id), orderId)
                intent.putExtra(getString(R.string.order_title), orderTitle)
                PendingIntent.getBroadcast(
                    requireContext(),
                    orderId.toInt(),
                    intent,
                    FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
                )
            }

        val deliveryTime = 5 * 1000
        alarmManager.setExact(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + deliveryTime,
            alarmIntent
        )
    }

    companion object {
        const val freeShipping = 40000
        const val shipping = 2500
        const val minimumPrice = 10000

        const val minimumCount = 1
        const val maximumCount = 100
    }
}