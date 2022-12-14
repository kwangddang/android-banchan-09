package com.woowa.banchan.ui.common.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartAddBinding
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.key.FOOD
import com.woowa.banchan.ui.common.popup.CartCompleteFragment
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CartAddFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCartAddBinding

    private var foodItem: FoodItem? = null
    private var totalPrice: Int? = null

    private val viewModel: CartAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFood()
        initBinding()
        initObserver()
    }

    private fun initFood() {
        foodItem = arguments?.getSerializable(FOOD) as FoodItem
        totalPrice = foodItem?.sPrice
    }

    private fun initBinding() {
        binding.apply {
            vm = viewModel
            food = foodItem
            price = totalPrice
            count = viewModel.totalCount
        }
    }

    private fun initObserver() {
        viewModel.insertionUiState.flowWithLifecycle(lifecycle)
            .onEach { state ->
                if (state is UiState.Success) {
                    parentFragmentManager.commit { remove(this@CartAddFragment) }
                    CartCompleteFragment().show(
                        parentFragmentManager,
                        getString(R.string.fragment_cart_complete)
                    )
                } else if (state is UiState.Error) {
                    showToast(state.error.message)
                }
            }.launchIn(lifecycleScope)

        viewModel.cancelClickEvent.observe(viewLifecycleOwner, EventObserver { dismiss() })

        viewModel.plusClickEvent.observe(viewLifecycleOwner, EventObserver {
            if (viewModel.totalCount < 100)
                viewModel.totalCount++
            totalPrice = viewModel.totalCount * foodItem!!.sPrice
            setCountAndPrice()
        })

        viewModel.minusClickEvent.observe(viewLifecycleOwner, EventObserver {
            if (viewModel.totalCount > 1)
                viewModel.totalCount--
            totalPrice = viewModel.totalCount * foodItem!!.sPrice
            setCountAndPrice()
        })
    }

    private fun setCountAndPrice() {
        binding.count = viewModel.totalCount
        binding.price = totalPrice
    }
}