package br.com.aldemir.common

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun getDeviceLanguage(): String {
    return NSLocale.currentLocale.languageCode
}