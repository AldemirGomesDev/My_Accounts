package br.com.aldemir.common

import android.annotation.SuppressLint
import android.app.Activity

@SuppressLint("StaticFieldLeak")
actual object PlatformActivity {
    private lateinit var currentActivity: Activity

    fun init(activity: Activity) {
        this.currentActivity = activity
    }

    actual fun getCurrentActivity(): Any = currentActivity

    actual fun moveAppToBackground() {
        currentActivity.moveTaskToBack(true)
    }
}