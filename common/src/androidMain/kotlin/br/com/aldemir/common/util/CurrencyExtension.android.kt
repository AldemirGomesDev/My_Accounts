package br.com.aldemir.common.util


import java.util.Currency
import java.util.Locale

actual fun getCurrencySymbol(language: String, countryCode: String): String {
    return Currency.getInstance(Locale(language, countryCode)).symbol
}

actual fun Double.toCurrency(currencySymbol: String): String {
    val formatted = "%,.2f".format(this)
    return "$currencySymbol $formatted"
}