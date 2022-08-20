package com.woowa.banchan.ui.detail

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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentCartUpdateBinding
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.popup.CartCompleteFragment
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CartUpdateFragment : DialogFragment() {

    private lateinit var binding: FragmentCartUpdateBinding

    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart_update, container, false)
        initDialog()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initButtonSetting()
        initObserver()
    }

    private fun initBinding() {
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    private fun initDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    private fun initButtonSetting() {
        binding.tvConfirm.setOnClickListener { }
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initObserver() {
        viewModel.updateUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                Log.d("Test", it.toString())
                val state = it.getContentIfNotHandled()
                if (state is UiState.Success) {
                    parentFragmentManager.commit { remove(this@CartUpdateFragment) }
                    CartCompleteFragment().show(parentFragmentManager, getString(R.string.fragment_cart_update))
                } else if (state is UiState.Error) {
                    showToast(state.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.cancelClickEvent.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })
    }

}