package com.pizza.application.util

import java.text.DecimalFormat

fun String?.emptyIfNull(): String {
    return this ?: ""
}

fun Double?.toStringPrice(): String {
    return this?.let{
        val numberFormat = DecimalFormat("#,##0.00")
        "$ ${numberFormat.format(this)}"
    } ?: ""
}