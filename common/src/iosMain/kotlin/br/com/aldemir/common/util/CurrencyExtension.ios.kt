package br.com.aldemir.common.util

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat
import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleCurrencySymbol

actual fun getCurrencySymbol(language: String, countryCode: String): String {
    val localeId = "${language}_${countryCode}"
    val locale = NSLocale(localeIdentifier = localeId)
    return locale.objectForKey(NSLocaleCurrencySymbol) as? String ?: "¤"
}

actual fun Double.toCurrency(currencySymbol: String): String {
    val formatted = NSString.stringWithFormat("%.2f", this)
    return "$currencySymbol $formatted"
}