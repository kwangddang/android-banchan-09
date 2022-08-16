package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemCartBinding
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.emptyCart

class CartContentViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var onClickCartCheckState: (cart: Cart) -> Unit = {}
    var onClickCartUpdateCount: (cart: Cart) -> Unit = {}
    var onClickCartRemove: (cart: Cart) -> Unit = {}

    private var cart = emptyCart()

    fun bind(cart: Cart) {
        if (cart.hash == "empty") {
            binding.tvEmptyNotice.isVisible = true
            binding.layoutCartContent.isGone = true
        } else
            binding.cart = cart

        this.cart = cart
        initButton()
    }

    private fun initButton() {

        binding.layoutCheckBtn.setOnClickListener {
            cart.checkState = cart.checkState.not()
            binding.cart = cart
            onClickCartCheckState(cart)
        }
        binding.ivMinusCount.setOnClickListener {
            cart.count = if (cart.count <= 1) 1 else cart.count - 1
            binding.cart = cart
            onClickCartUpdateCount(cart)
        }
        binding.ivPlusCount.setOnClickListener {
            cart.count++
            binding.cart = cart
            onClickCartUpdateCount(cart)
        }
        binding.ivRemove.setOnClickListener { onClickCartRemove(cart) }
    }
}