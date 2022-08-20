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
import com.woowa.banchan.ui.common.popup.CartCompleteFragment
import com.woowa.banchan.ui.common.uistate.UiState
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

    private var foodTitle: String? = null
    private var hash: String? = null

    private var sPrice = 0

    private var totalPrice = 0
    private var totalCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        getIntentValues()
        initBinding()
        initViews()
        initObserver()
        initButtonSetting()
    }

    private fun getIntentValues() {
        foodTitle = intent.getStringExtra("title")!!
        hash = intent.getStringExtra("hash")!!
        if (foodTitle == null || hash == null) finish()
    }

    private fun initBinding() {
        binding.apply {
            title = foodTitle
            count = totalCount
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
                if (state is UiState.Success) {
                    sPrice = state.data.sPrice
                    binding.price = sPrice
                    binding.detail = state.data
                    binding.vpDetail.adapter = DetailVPAdapter(state.data.thumbImages)
                    binding.indicatorDetail.attachTo(binding.vpDetail)

                    val detailRVAdapter = DetailRVAdapter(state.data.detailSection)
                    binding.rvDetail.adapter = detailRVAdapter
                    detailRVAdapter.notifyDataSetChanged()
                    viewModel.insertRecentlyViewed(foodTitle!!, totalCount)
                } else if (state is UiState.Error) {

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
                    showToast(state.message)
                }
            }.launchIn(lifecycleScope)

        viewModel.cartClickEvent.observe(this) {
            startActivity(Intent(this, CartActivity::class.java))
        }

        viewModel.userClickEvent.observe(this) {
            startActivity(Intent(this, OrderActivity::class.java))
        }
    }

    private fun initButtonSetting() {
        binding.ivPlus.setOnClickListener {
            totalCount++
            totalPrice = totalCount * sPrice
            setCountAndPrice()
        }

        binding.ivMinus.setOnClickListener {
            if (totalCount > 1) {
                totalCount--
                totalPrice = totalCount * sPrice
                setCountAndPrice()
            }
        }

        binding.btnOrder.setOnClickListener { viewModel.insertCart(foodTitle!!, totalCount) }
    }

    private fun setCountAndPrice() {
        binding.count = totalCount
        binding.price = totalPrice
    }
}