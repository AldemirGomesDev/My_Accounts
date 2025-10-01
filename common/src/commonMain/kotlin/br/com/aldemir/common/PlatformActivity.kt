package br.com.aldemir.common

expect object PlatformActivity {
    fun getCurrentActivity(): Any
    fun moveAppToBackground()
}