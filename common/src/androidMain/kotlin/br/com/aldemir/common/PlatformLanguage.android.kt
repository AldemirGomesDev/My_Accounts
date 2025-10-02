package br.com.aldemir.common

import java.util.Locale

actual fun getDeviceLanguage(): String {
    return Locale.getDefault().language
}