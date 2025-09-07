package br.com.aldemir.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostDTO(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)
