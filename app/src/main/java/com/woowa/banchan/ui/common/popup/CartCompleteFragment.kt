package com.woowa.banchan.ui.common.popup

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartCompleteBinding
import com.woowa.banchan.ui.cart.CartActivity

class CartCompleteFragment : DialogFragment() {

    private lateinit var binding: FragmentCartCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_complete, container, false)
        initDialog()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonSetting()
    }

    private fun initDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private fun initButtonSetting() {
        binding.tvCartConfirm.setOnClickListener {
            parentFragmentManager.commit { remove(this@CartCompleteFragment) }
            startActivity(Intent(context,CartActivity::class.java))
        }
        binding.tvContinueShopping.setOnClickListener {
            dismiss()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}