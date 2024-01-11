package br.com.aldemir.data.database.model

data class RecipeMonthlyDomain(
    var id: Int = 0,
    var id_recipe: Int = 0,
    var name: String = "",
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var due_date: Int = 0,
    var status: Boolean = false,
)
