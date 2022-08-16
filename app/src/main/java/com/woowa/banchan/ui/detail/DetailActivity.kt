package com.woowa.banchan.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityDetailBinding
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.detail.adapter.DetailRVAdapter
import com.woowa.banchan.ui.detail.adapter.DetailVPAdapter
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private var title: String = ""
    private var hash: String = ""

    private var sPrice = 0

    private var totalPrice = 0
    private var totalCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        getIntentValues()
        initBinding()
        initViews()
        initObserve()
        initButtonSetting()
    }

    private fun getIntentValues() {
        title = intent.getStringExtra("title")!!
        hash = intent.getStringExtra("hash")!!
    }

    private fun initBinding() {
        binding.title = title
        binding.count = totalCount
    }

    private fun initViews() {
        viewModel.getDetailFood(hash)
    }

    private fun initObserve() {
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
                } else if (state is UiState.Error) {

                }
            }.launchIn(lifecycleScope)

        viewModel.insertionUiState.flowWithLifecycle(lifecycle)
        viewModel.insertionUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {

                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(lifecycleScope)
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

        binding.btnOrder.setOnClickListener { viewModel.insertCart(title, totalCount) }
    }

    private fun setCountAndPrice() {
        binding.count = totalCount
        binding.price = totalPrice
    }
}