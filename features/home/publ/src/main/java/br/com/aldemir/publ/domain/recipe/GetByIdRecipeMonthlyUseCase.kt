package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipePerMonthDTO

interface GetByIdRecipeMonthlyUseCase {
    suspend operator fun invoke(id: Int): RecipePerMonthDTO
}