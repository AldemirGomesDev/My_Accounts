package br.com.aldemir.domain.model

data class RecipeMonthlyDomain(
    var id: Int = 0,
    var id_recipe: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var status: Boolean = false
)
