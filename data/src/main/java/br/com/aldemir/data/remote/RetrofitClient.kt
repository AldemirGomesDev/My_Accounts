package br.com.aldemir.data.remote

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val BASE_URL = "https://fakestoreapi.com/"

    fun provideRetrofit(cache: Cache): Retrofit {
       return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(cache))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(cache: Cache): OkHttpClient {
//        cache.evictAll()
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(ResponseCacheControlInterceptor())
            .build()
    }
}