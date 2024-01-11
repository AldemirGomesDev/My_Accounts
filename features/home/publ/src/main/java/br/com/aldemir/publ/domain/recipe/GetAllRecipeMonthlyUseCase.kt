package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeMonthlyDTO

interface GetAllRecipeMonthlyUseCase {
    suspend operator fun invoke(month: String, year: String): List<RecipeMonthlyDTO>
}