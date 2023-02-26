package br.com.aldemir.myaccounts.presentation.shared.model

import java.util.*

data class RecipeView(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var created_at: Date? = null,
    var due_date: Int = 0,
    var status: Boolean = false,
    var expired: Boolean = false
)
