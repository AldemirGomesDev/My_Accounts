package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.model.RecipeUpdateDomain
import br.com.aldemir.domain.repository.RecipeRepository

class UpdateRecipeNameAndDescriptionUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
): UpdateRecipeNameAndDescriptionUseCase {
    override suspend fun invoke(recipeUpdateDomain: RecipeUpdateDomain): Int {
        return recipeRepository.updateNameDescription(recipeUpdateDomain)
    }
}