package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeMonthlyDTO

interface AddRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthlyDTO: RecipeMonthlyDTO): Long
}