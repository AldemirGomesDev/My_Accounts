package br.com.aldemir.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface ApiService {
    suspend fun getPosts(): List<PostDTO>
    suspend fun getProducts(): List<ProductDTO>
}

class ApiServiceImpl(
    private val httpClient: HttpClient
): ApiService {
    override suspend fun getPosts(): List<PostDTO> {
        return httpClient.get("posts").body<List<PostDTO>>()
    }

    override suspend fun getProducts(): List<ProductDTO> {
        return httpClient.get("products").body<List<ProductDTO>>()
    }

}