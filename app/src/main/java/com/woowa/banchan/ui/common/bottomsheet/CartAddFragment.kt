package com.woowa.banchan.ui.common.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartAddBinding
import com.woowa.banchan.domain.model.FoodItem

class CartAddFragment(private val foodItem: FoodItem) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCartAddBinding

    private var totalPrice = foodItem.sPrice
    private var totalCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initButtonSetting()
    }

    private fun initBinding() {
        binding.apply {
            food = foodItem
            price = totalPrice
            count = totalCount
        }
    }

    private fun initButtonSetting() {
        binding.tvCancel.setOnClickListener { dismiss() }
        binding.ivPlus.setOnClickListener {
            totalCount++
            totalPrice = totalCount * foodItem.sPrice
            setCountAndPrice()
        }

        binding.ivMinus.setOnClickListener {
            if(totalCount > 1) {
                totalCount--
                totalPrice = totalCount * foodItem.sPrice
                setCountAndPrice()
            }
        }
    }

    private fun setCountAndPrice() {
        binding.count = totalCount
        binding.price = totalPrice
    }
}