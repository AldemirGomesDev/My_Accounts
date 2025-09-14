package br.com.aldemir.data.remote

import br.com.aldemir.data.config.isDebug
import com.diamondedge.logging.logging
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.toMap
import kotlinx.serialization.json.Json

object MyAccountHttpClient {
//    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val BASE_URL = "https://fakestoreapi.com/"
    private val log = logging("MyAccountHttpClient")

    internal fun provideHttpClient(
        interceptor: ResponseCacheControlInterceptor
    ) : HttpClient {
        val httpClient = HttpClient(httpClientEnginePlatform()) {
            defaultRequest {
                url(BASE_URL)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
            if (isDebug()) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            log.error { message }
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
            HttpResponseValidator {
                validateResponse { response ->
                    val statusCode = response.status.value
                    if (statusCode > 300) {
                        log.error { "HTTP error with status code: $statusCode" }
                    }
                    val headersWithoutTarget = Headers.build {
                        response.headers.toMap().forEach { (key, values) ->
                            if (!key.equals("ETag", ignoreCase = true)) {
                                appendAll(key, values)
                            }
                        }
                    }
                    log.info { headersWithoutTarget }
                }
            }
        }
        httpClient.plugin(HttpSend).intercept(interceptor.provideInterceptor())

        return httpClient
    }
}