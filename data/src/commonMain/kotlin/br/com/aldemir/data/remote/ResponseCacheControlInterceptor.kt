package br.com.aldemir.data.remote

import okhttp3.Interceptor
import okhttp3.Response

private const val DAY_IN_SECONDS = 60
private const val E_TAG = "ETag"
private const val EXPIRES = "Expires"
private const val PRAGMA = "Pragma"
private const val CACHE_CONTROL_NAME = "Cache-Control"
private const val CACHE_CONTROL_VALUE = "max-age=$DAY_IN_SECONDS"

internal class ResponseCacheControlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .removeHeader(CACHE_CONTROL_NAME)
            .removeHeader(E_TAG)
            .removeHeader(EXPIRES)
            .removeHeader(PRAGMA)
            .header(CACHE_CONTROL_NAME, CACHE_CONTROL_VALUE)
            .build()
    }
}