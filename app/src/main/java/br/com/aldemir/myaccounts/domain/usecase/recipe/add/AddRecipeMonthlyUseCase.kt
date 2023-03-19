package br.com.aldemir.myaccounts.domain.usecase.recipe.add

import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO

interface AddRecipeMonthlyUseCase {
    suspend operator fun invoke(recipeMonthlyDTO: RecipeMonthlyDTO): Long
}