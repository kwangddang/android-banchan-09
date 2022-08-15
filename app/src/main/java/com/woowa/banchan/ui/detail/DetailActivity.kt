package com.woowa.banchan.ui.detail

import android.os.Bundle
import android.util.Log
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var title: String
    private lateinit var hash: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        getIntentValue()
        initViews()
        initAdapter()
        initObserve()
    }

    private fun getIntentValue() {
        title = intent.getStringExtra("title")!!
        hash = intent.getStringExtra("hash")!!
    }

    private fun initAdapter() {

    }

    private fun initViews() {
        binding.title = title
        viewModel.getDetailFood(hash)
    }

    private fun initObserve() {
        viewModel.detailUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    binding.detail = state.data
                    binding.vpDetail.adapter = DetailVPAdapter(state.data.thumbImages)
                    binding.indicatorDetail.attachTo(binding.vpDetail)

                    val detailRVAdapter = DetailRVAdapter(state.data.detailSection)
                    binding.rvDetail.adapter = detailRVAdapter
                    detailRVAdapter.notifyDataSetChanged()
                } else if (state is UiState.Error) {

                }
            }.launchIn(lifecycleScope)
    }
}