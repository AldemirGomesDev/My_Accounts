package br.com.aldemir.domain.model

data class RecipeUpdateDomain(
    var id: Int = 0,
    var name: String = "",
    var description: String = "",
)
