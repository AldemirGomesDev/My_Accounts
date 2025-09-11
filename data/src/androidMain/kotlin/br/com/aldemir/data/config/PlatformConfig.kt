package br.com.aldemir.data.config

import br.com.aldemir.data.BuildConfig

actual object PlatformConfig {
    actual val isDebug: Boolean = BuildConfig.DEBUG
}