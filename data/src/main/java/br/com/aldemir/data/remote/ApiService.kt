package br.com.aldemir.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<PostDTO>

    @GET("products")
    suspend fun getProducts(): List<ProductDTO>
}