package br.com.aldemir.myaccounts.domain.usecase.recipe.delete

import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepository
import javax.inject.Inject

class DeleteRecipeUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
): DeleteRecipeUseCase {
    override suspend fun invoke(recipe: Recipe): Int {
       return recipeRepository.delete(recipe)
    }
}