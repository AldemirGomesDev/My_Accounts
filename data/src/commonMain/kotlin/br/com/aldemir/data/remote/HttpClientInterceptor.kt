package br.com.aldemir.data.remote

import io.ktor.client.plugins.HttpSendInterceptor

fun interface HttpClientInterceptor {
    fun provideInterceptor(): HttpSendInterceptor
}