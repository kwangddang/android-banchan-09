package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.databinding.ItemCartBinding
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.emptyCart
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.maximumCount
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.minimumCount

class CartContentViewHolder(
    private val binding: ItemCartBinding,
    private val onClickCartStateChange: (cart: Cart) -> Unit,
    private val onClickCartCountChange: (cart: Cart) -> Unit,
    private val onClickCartRemove: (cart: Cart) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var cart = emptyCart()

    fun bind(cart: Cart) {
        if (cart.hash == "empty") {
            binding.tvEmptyNotice.isVisible = true
            binding.layoutCartContent.isGone = true
        } else {
            binding.cart = cart
            binding.itemCount = cart.count.toString()
        }

        this.cart = cart

        initButton()
        initEditText()
    }

    private fun initEditText() {
        binding.etCount.doAfterTextChanged { updateCount((it ?: "").toString()) }
    }

    private fun initButton() {

        binding.layoutCheckBtn.setOnClickListener {
            cart.checkState = cart.checkState.not()
            binding.cart = cart
            onClickCartStateChange(cart)
        }
        binding.ivMinusCount.setOnClickListener { updateCount(cart.count - 1) }
        binding.ivPlusCount.setOnClickListener { updateCount(cart.count + 1) }
        binding.ivRemove.setOnClickListener { onClickCartRemove(cart) }
    }

    private fun updateCount(count: Int) {
        cart.count = if (count < minimumCount) minimumCount else if (count > maximumCount) maximumCount else count

        binding.itemCount = cart.count.toString()
        binding.cart = cart
        onClickCartCountChange(cart)
    }

    private fun updateCount(count: String) {
        binding.itemCount = count
        val count = if (count.isEmpty()) 0 else count.toInt()

        cart.count = if (count < minimumCount) minimumCount else if (count > maximumCount) maximumCount else count
        binding.cart = cart
        onClickCartCountChange(cart)
    }

}