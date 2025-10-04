package br.com.aldemir.common.util

import kotlin.math.roundToInt

private val onlyNumberRegex by lazy { "[^0-9 ]".toRegex() }
private const val DECIMAL_FACTOR = 100

fun String.fromCurrency(): Double {
    val clean = this.replace(onlyNumberRegex, emptyString())
    return clean.toDoubleOrNull()?.div(DECIMAL_FACTOR) ?: 0.0
}

expect fun Double.toCurrency(currencySymbol: String): String

fun Float.toDecimal(): Float = roundToInt().toFloat() / DECIMAL_FACTOR

fun emptyFloat(): Float = 0.0f

fun Double.toPercentString(): String {
    if (this == 0.0) return emptyString()
    return (this * 100).toInt().toString()
}

expect fun getCurrencySymbol(language: String, countryCode: String): String


