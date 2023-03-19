package br.com.aldemir.myaccounts.domain.usecase.recipe.update

import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO

interface UpdateRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthlyDTO: RecipeMonthlyDTO): Int
}