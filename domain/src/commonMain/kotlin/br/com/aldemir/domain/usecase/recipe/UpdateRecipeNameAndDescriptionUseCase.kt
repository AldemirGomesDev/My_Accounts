package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeUpdateDomain
import br.com.aldemir.domain.repository.RecipeRepository

class UpdateRecipeNameAndDescriptionUseCase(
    private val recipeRepository: RecipeRepository
): UseCase<RecipeUpdateDomain, Int> {

    override suspend fun execute(params: RecipeUpdateDomain): Int {
        return recipeRepository.updateNameDescription(params)
    }
}