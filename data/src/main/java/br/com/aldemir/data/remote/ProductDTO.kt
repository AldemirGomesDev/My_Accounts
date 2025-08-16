package br.com.aldemir.data.remote

data class ProductDTO(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDTO
)

data class RatingDTO(
    val rate: Double,
    val count: Int
)