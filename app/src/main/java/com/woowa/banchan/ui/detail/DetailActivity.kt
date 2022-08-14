package com.woowa.banchan.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityDetailBinding
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.detail.adapter.DetailVPAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        initAdapter()
    }

    private fun initAdapter() {
        binding.vpDetail.adapter = DetailVPAdapter(
            listOf(
                "http://public.codesquad.kr/jk/storeapp/data/soup/28_ZIP_P_1003_T.jpg",
                "http://public.codesquad.kr/jk/storeapp/data/soup/28_ZIP_P_1003_S.jpg"
            )
        )
        binding.indicatorDetail.attachTo(binding.vpDetail)
    }

    private fun initObserve() {
        viewModel.detailUiState.flowWithLifecycle(this.lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {

                } else if (state is UiState.Error) {

                }
            }
    }
}