package com.woowa.banchan.domain.model

data class Cart(
    val hash: String,
    var checkState: Boolean,
    val price: Int,
    var count: Int,
    val title: String,
    val imageUrl: String,
)

fun emptyCart(): Cart = Cart(
    "empty",
    false,
    0,
    0,
    "",
    "https://i.insider.com/5484d9d1eab8ea3017b17e29?width=600&format=jpeg&auto=webp"
)