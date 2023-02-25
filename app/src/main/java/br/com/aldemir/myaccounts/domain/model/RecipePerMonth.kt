package br.com.aldemir.myaccounts.domain.model

data class RecipePerMonth(
    var id_recipe: Int = 0,
    var name: String = "",
    var description: String = "",
    var due_date: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double,
    var status: Boolean = false
)
