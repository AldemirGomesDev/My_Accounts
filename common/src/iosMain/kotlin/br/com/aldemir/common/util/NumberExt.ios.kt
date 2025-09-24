package br.com.aldemir.common.util

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSNumberFormatterRoundDown

actual fun Float.toPercentFormatted(): String {
    val formatter = NSNumberFormatter().apply {
        minimumFractionDigits = 1u
        maximumFractionDigits = 1u
        numberStyle = NSNumberFormatterDecimalStyle
        roundingMode = NSNumberFormatterRoundDown
    }
    return formatter.stringFromNumber(NSNumber(this * 100))!! + "%"
}