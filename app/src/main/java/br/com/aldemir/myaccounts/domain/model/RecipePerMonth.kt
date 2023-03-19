package br.com.aldemir.myaccounts.domain.model

data class RecipePerMonth(
    var id: Int = 0,
    var id_recipe: Int = 0,
    var name: String = "",
    var description: String = "",
    var due_date: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var status: Boolean = false
)
