package br.com.aldemir.myaccounts.domain.usecase.recipe.add

import br.com.aldemir.myaccounts.data.model.RecipeDTO

interface AddRecipeUseCase {
    suspend operator fun invoke(recipeDTO: RecipeDTO): Long
}