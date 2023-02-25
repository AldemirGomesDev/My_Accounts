package br.com.aldemir.myaccounts.util

import java.text.DecimalFormat
import kotlin.math.roundToInt

private val onlyNumberRegex by lazy { "[^0-9 ]".toRegex() }
private const val DECIMAL_FACTOR = 100
private const val CURRENCY_PATTERN = "R$ #,###,##0.00"

fun String.fromCurrency(): Double = this
    .replace(onlyNumberRegex, "")
    .toDouble()
    .div(DECIMAL_FACTOR)

fun Double.toCurrency(): String = DecimalFormat(CURRENCY_PATTERN)
    .format(this)

fun Float.toDecimal(): Float = roundToInt().toFloat() / 100

fun emptyFloat(): Float = 0.0f

fun maskCurrency(s: String?): String {

    val stringValue = s.toString()

    return if (stringValue != emptyString()) {

        val doubleValue = stringValue.fromCurrency()

        getFormattedValue(doubleValue)

    } else emptyString()
}

private fun getFormattedValue(value: Double): String = if (value == 0.0) {
    emptyString()
} else value.toCurrency()


