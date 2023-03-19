package br.com.aldemir.myaccounts.domain.usecase.recipe.add

import br.com.aldemir.myaccounts.data.model.RecipeDTO
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepository
import javax.inject.Inject

class AddRecipeUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
) : AddRecipeUseCase {
    override suspend fun invoke(recipeDTO: RecipeDTO): Long {
        return recipeRepository.insert(recipeDTO)
    }
}