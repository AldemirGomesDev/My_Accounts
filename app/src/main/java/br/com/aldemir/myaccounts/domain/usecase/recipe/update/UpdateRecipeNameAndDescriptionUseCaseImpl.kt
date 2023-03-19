package br.com.aldemir.myaccounts.domain.usecase.recipe.update

import br.com.aldemir.myaccounts.data.model.RecipeUpdateDTO
import br.com.aldemir.myaccounts.data.repository.recipe.RecipeRepository
import javax.inject.Inject

class UpdateRecipeNameAndDescriptionUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository
): UpdateRecipeNameAndDescriptionUseCase {
    override suspend fun invoke(recipeUpdateDTO: RecipeUpdateDTO): Int {
        return recipeRepository.updateNameDescription(recipeUpdateDTO)
    }
}