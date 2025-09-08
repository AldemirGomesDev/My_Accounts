package br.com.aldemir.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDTO
)

@Serializable
data class RatingDTO(
    val rate: Double,
    val count: Int
)