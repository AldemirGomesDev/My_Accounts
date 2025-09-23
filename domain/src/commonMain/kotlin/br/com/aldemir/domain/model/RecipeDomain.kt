package br.com.aldemir.domain.model

import kotlinx.datetime.Instant

data class RecipeDomain(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var created_at: Instant? = null,
    var due_date: Int = 0,
    var status: Boolean = false
)
