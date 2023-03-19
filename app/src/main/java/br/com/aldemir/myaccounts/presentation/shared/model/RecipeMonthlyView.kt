package br.com.aldemir.myaccounts.presentation.shared.model

import br.com.aldemir.myaccounts.util.emptyString

data class RecipeMonthlyView(
    var id: Int = 0,
    var id_recipe: Int = 0,
    var name: String = emptyString(),
    var year: String = emptyString(),
    var month: String = emptyString(),
    var value: Double = 0.0,
    var due_date: Int = 0,
    var status: Boolean = false,
    var expired: Boolean = false
)
