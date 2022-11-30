package br.com.aldemir.myaccounts.util


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
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

