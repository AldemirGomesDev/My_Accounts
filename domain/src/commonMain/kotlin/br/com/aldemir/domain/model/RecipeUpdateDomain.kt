package br.com.aldemir.domain.model

data class RecipeUpdateDomain(
    var id: Int = 0,
    var idMonthlyRecipe: Int = 0,
    var name: String = "",
    val value: Double = 0.0,
    val isPaid: Boolean = false,
    var description: String = "",
)
