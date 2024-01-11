package br.com.aldemir.home.domain.usecase.recipe.delete

import br.com.aldemir.publ.domain.recipe.DeleteRecipeUseCase
import br.com.aldemir.data.repository.recipe.RecipeRepository

class DeleteRecipeUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
): DeleteRecipeUseCase {
    override suspend fun invoke(recipeDTO: br.com.aldemir.data.database.model.RecipeDTO): Int {
       return recipeRepository.delete(recipeDTO)
    }
}