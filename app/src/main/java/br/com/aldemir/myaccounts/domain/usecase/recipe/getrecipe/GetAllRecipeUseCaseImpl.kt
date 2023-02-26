package br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipe

import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepository
import javax.inject.Inject

class GetAllRecipeUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
) : GetAllRecipeUseCase {
    override suspend fun invoke(): List<Recipe> {
        return recipeRepository.getAll()
    }
}