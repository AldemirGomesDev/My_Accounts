package br.com.aldemir.common

import android.content.Context
import android.widget.Toast

actual fun showMessage(message: String) {
    val context: Context = PlatformContext.getAppContext() as Context
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()
}