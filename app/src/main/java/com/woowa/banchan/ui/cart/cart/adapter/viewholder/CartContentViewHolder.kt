package com.woowa.banchan.ui.cart.cart.adapter.viewholder

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ItemCartBinding
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.emptyCart
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.maximumCount
import com.woowa.banchan.ui.cart.cart.CartFragment.Companion.minimumCount

class CartContentViewHolder(
    private val binding: ItemCartBinding,
    private val onClickCartCheckState: (cart: Cart) -> Unit,
    private val onClickCartUpdateCount: (cart: Cart, message: Int?) -> Unit,
    private val onClickCartRemove: (cart: Cart) -> Unit
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
            onClickCartCheckState(cart)
        }
        binding.ivMinusCount.setOnClickListener { updateCount(cart.count - 1) }
        binding.ivPlusCount.setOnClickListener { updateCount(cart.count + 1) }
        binding.ivRemove.setOnClickListener { onClickCartRemove(cart) }
    }

    private fun updateCount(c: Int) {
        val msg: Int? = if (c < minimumCount || c > maximumCount) overflowMessage else null
        cart.count =
            if (c < minimumCount) minimumCount else if (c > maximumCount) maximumCount else c

        binding.itemCount = cart.count.toString()
        binding.cart = cart
        onClickCartUpdateCount(cart, msg)
    }

    private fun updateCount(c: String) {
        binding.itemCount = c
        val count = if (c.isEmpty()) 0 else c.toInt()

        val msg: Int? =
            if (count < minimumCount || count > maximumCount) overflowMessage else null
        cart.count =
            if (count < minimumCount) minimumCount else if (count > maximumCount) maximumCount else count
        binding.cart = cart
        onClickCartUpdateCount(cart, msg)
    }

    private val overflowMessage = R.string.error_item_count_overflow
}