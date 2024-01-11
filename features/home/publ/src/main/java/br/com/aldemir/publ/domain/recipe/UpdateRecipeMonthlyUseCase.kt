package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeMonthlyDTO

interface UpdateRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthlyDTO: RecipeMonthlyDTO): Int
}