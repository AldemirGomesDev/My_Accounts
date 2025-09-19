package br.com.aldemir.common

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
actual object PlatformContext {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    actual fun getAppContext(): Any = context
}