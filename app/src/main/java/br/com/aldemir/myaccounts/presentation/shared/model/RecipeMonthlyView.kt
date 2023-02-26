package br.com.aldemir.myaccounts.presentation.shared.model

data class RecipeMonthlyView(
    var id: Int = 0,
    var id_recipe: Int = 0,
    var year: String = "",
    var month: String = "",
    var value: Double = 0.0,
    var due_date: Int = 0,
    var status: Boolean = false,
    var expired: Boolean = false
)
