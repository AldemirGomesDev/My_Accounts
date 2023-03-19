package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipe

import br.com.aldemir.myaccounts.data.model.RecipeDTO

interface GetAllRecipeUseCase {
    suspend operator fun invoke(): List<RecipeDTO>
}