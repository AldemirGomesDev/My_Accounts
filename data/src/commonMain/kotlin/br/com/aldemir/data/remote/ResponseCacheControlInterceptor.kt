package br.com.aldemir.data.remote

import io.ktor.client.plugins.HttpSendInterceptor

private const val DAY_IN_SECONDS = 60
private const val E_TAG = "ETag"
private const val EXPIRES = "Expires"
private const val PRAGMA = "Pragma"
private const val CACHE_CONTROL_NAME = "Cache-Control"
private const val CACHE_CONTROL_VALUE = "max-age=$DAY_IN_SECONDS"

internal class ResponseCacheControlInterceptor : HttpClientInterceptor {
    override fun provideInterceptor(): HttpSendInterceptor {
        return { httpRequestBuilder ->
            httpRequestBuilder.headers.remove(E_TAG)
            httpRequestBuilder.headers.remove(CACHE_CONTROL_NAME)
            httpRequestBuilder.headers.remove(EXPIRES)
            httpRequestBuilder.headers.remove(PRAGMA)
            httpRequestBuilder.headers.append(CACHE_CONTROL_NAME, CACHE_CONTROL_VALUE)
            execute(httpRequestBuilder)
        }
    }
}
