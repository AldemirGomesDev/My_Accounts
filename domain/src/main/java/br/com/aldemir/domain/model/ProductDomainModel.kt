package br.com.aldemir.domain.model

data class ProductDomainModel(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingDomainModel
)

data class RatingDomainModel(
    val rate: Double,
    val count: Int
)
