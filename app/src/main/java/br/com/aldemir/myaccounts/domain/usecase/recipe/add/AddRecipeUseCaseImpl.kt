package br.com.aldemir.myaccounts.domain.usecase.recipe.add

import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepository
import javax.inject.Inject

class AddRecipeUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
) : AddRecipeUseCase {
    override suspend fun invoke(recipe: Recipe): Long {
        return recipeRepository.insert(recipe)
    }
}