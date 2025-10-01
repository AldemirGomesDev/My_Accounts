package br.com.aldemir.common

actual object PlatformActivity {
    actual fun getCurrentActivity(): Any = Any()
    actual fun moveAppToBackground() {}
}