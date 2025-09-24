package br.com.aldemir.common.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

actual fun Float.toPercentFormatted(): String {
    val symbols = DecimalFormatSymbols().apply {
        decimalSeparator = ','
    }
    return DecimalFormat("##0.0", symbols).apply {
        roundingMode = RoundingMode.DOWN
    }.format(this * 100) + "%"
}
