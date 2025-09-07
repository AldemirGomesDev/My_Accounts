package br.com.aldemir.data.remote

import android.util.Log
import br.com.aldemir.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object RetrofitClient {
//    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val BASE_URL = "https://fakestoreapi.com/"

    internal fun provideHttpClient(
        interceptor: ResponseCacheControlInterceptor
    ) : HttpClient = HttpClient(OkHttp) {
        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
        if (BuildConfig.DEBUG) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("HTTP_Client", message)
                    }
                }
                level = LogLevel.BODY
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    explicitNulls = false
                }
            )
        }
        install(HttpCache)
        engine {
            addNetworkInterceptor(interceptor)
        }
    }
}