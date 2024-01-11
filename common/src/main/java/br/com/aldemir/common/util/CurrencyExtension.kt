package br.com.aldemir.common.util

import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale
import kotlin.math.roundToInt

private val onlyNumberRegex by lazy { "[^0-9 ]".toRegex() }
private const val DECIMAL_FACTOR = 100

fun String.fromCurrency(): Double = this
    .replace(onlyNumberRegex, "")
    .toDouble()
    .div(DECIMAL_FACTOR)

fun Double.toCurrency(currencySymbol: String): String {
    val currentPattern = "$currencySymbol #,###,##0.00"
    return DecimalFormat(currentPattern)
        .format(this)
}

fun Float.toDecimal(): Float = roundToInt().toFloat() / 100

fun emptyFloat(): Float = 0.0f

fun getCurrencySymbol(language: String, countryCode: String): String {
    return Currency.getInstance(Locale(language, countryCode)).symbol
}


