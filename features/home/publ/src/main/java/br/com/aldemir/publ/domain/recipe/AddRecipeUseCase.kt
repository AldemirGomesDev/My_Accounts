package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeDTO

interface AddRecipeUseCase {
    suspend operator fun invoke(recipeDTO: RecipeDTO): Long
}