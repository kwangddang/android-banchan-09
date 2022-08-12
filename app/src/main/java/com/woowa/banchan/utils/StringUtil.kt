package com.woowa.banchan.utils

import java.text.NumberFormat
import java.util.*
import kotlin.math.absoluteValue

object StringUtil {

    private val format: NumberFormat = NumberFormat.getCurrencyInstance()
        .apply {
            maximumFractionDigits = 0
            currency = Currency.getInstance("KRW")
        }

    fun getPriceFormatString(price: Int): String =
        format.format(price.absoluteValue).removeRange(0, 1)

    fun getMoneyFormatString(price: Int): String =
        getPriceFormatString(price) + '원'
}