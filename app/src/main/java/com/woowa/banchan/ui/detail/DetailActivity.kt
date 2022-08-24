package com.woowa.banchan.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityDetailBinding
import com.woowa.banchan.ui.cart.CartActivity
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.popup.CartCompleteFragment
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.common.viewutils.showContent
import com.woowa.banchan.ui.common.viewutils.showError
import com.woowa.banchan.ui.common.viewutils.showLoading
import com.woowa.banchan.ui.detail.adapter.DetailRVAdapter
import com.woowa.banchan.ui.detail.adapter.DetailVPAdapter
import com.woowa.banchan.ui.order.OrderActivity
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private var hash: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        getIntentValues()
        initBinding()
        initViews()
        initObserver()
    }

    private fun getIntentValues() {
        viewModel.title.value = intent.getStringExtra("title")!!
        hash = intent.getStringExtra("hash")!!
        if (viewModel.title.value == null || hash == null) finish()
    }

    private fun initBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@DetailActivity
        }
    }

    private fun initViews() {
        viewModel.getDetailFood(hash!!)
    }

    private fun initObserver() {
        viewModel.detailUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Success -> {
                        showContent(binding.nsvDetail, binding.pbLoading, binding.evNetwork)
                        viewModel.sPrice.value = state.data.sPrice
                        viewModel.detailItem.value = state.data
                        binding.vpDetail.adapter = DetailVPAdapter(state.data.thumbImages)
                        binding.indicatorDetail.attachTo(binding.vpDetail)

                        val detailRVAdapter = DetailRVAdapter(state.data.detailSection)
                        binding.rvDetail.adapter = detailRVAdapter
                        detailRVAdapter.notifyDataSetChanged()
                        viewModel.insertRecentlyViewed(
                            viewModel.title.value!!,
                            viewModel.totalCount.value!!
                        )
                    }
                    is UiState.Error -> {
                        showError(binding.nsvDetail, binding.pbLoading, binding.evNetwork)
                        showToast(state.message)
                    }
                    is UiState.Loading -> showLoading(
                        binding.nsvDetail,
                        binding.pbLoading,
                        binding.evNetwork
                    )
                    else -> {}
                }
            }.launchIn(lifecycleScope)

        viewModel.insertionUiState.flowWithLifecycle(lifecycle)
            .onEach {
                val state = it.getContentIfNotHandled()

                if (state is UiState.Success) {
                    CartCompleteFragment().show(
                        supportFragmentManager,
                        getString(R.string.fragment_cart_complete)
                    )
                } else if (state is UiState.Error) {
                    if (state.message?.substring(0, 6) == "UNIQUE") {
                        CartUpdateFragment().show(
                            supportFragmentManager,
                            getString(R.string.fragment_cart_update)
                        )
                    } else {
                        showToast(state.message)
                    }
                }
            }.launchIn(lifecycleScope)

        viewModel.cartCountUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if (state is UiState.Success)
                    binding.ctbToolbar.setCartCountIcon(state.data)
                else if (state is UiState.Error)
                    showToast(state.message)

            }.launchIn(lifecycleScope)

        viewModel.orderStateUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    if (state.data)
                        binding.ctbToolbar.setUserNotifierIcon()
                    else
                        binding.ctbToolbar.unSetUserNotifierIcon()
                } else if (state is UiState.Error)
                    showToast(state.message)

            }.launchIn(lifecycleScope)

        viewModel.cartClickEvent.observe(this, EventObserver {
            startActivity(Intent(this, CartActivity::class.java))
        })

        viewModel.userClickEvent.observe(this, EventObserver {
            startActivity(Intent(this, OrderActivity::class.java))
        })
    }

}