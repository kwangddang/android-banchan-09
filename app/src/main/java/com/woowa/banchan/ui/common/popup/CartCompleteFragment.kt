package com.woowa.banchan.ui.common.popup

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartCompleteBinding
import com.woowa.banchan.ui.cart.CartActivity
import com.woowa.banchan.ui.common.event.EventObserver

class CartCompleteFragment : DialogFragment() {

    private lateinit var binding: FragmentCartCompleteBinding

    private val viewModel: CartCompleteViewModel by viewModels()

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
        initBinding()
        initObserver()
    }

    private fun initBinding() {
        binding.vm = viewModel
    }

    private fun initDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private fun initObserver() {
        viewModel.confirmClickEvent.observe(viewLifecycleOwner, EventObserver {
            parentFragmentManager.commit { remove(this@CartCompleteFragment) }
            if (this.requireActivity() is CartActivity) this.requireActivity().finish()
            startActivity(Intent(context, CartActivity::class.java))
        })

        viewModel.continueClickEvent.observe(viewLifecycleOwner, EventObserver { dismiss() })
    }

}