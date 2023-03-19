package br.com.aldemir.myaccounts.domain.usecase.recipe.delete

import br.com.aldemir.myaccounts.data.model.RecipeDTO
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepository
import javax.inject.Inject

class DeleteRecipeUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
): DeleteRecipeUseCase {
    override suspend fun invoke(recipeDTO: RecipeDTO): Int {
       return recipeRepository.delete(recipeDTO)
    }
}