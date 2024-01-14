package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipeUpdateDomain
import br.com.aldemir.domain.repository.RecipeRepository

class UpdateRecipeNameAndDescriptionUseCase constructor(
    private val recipeRepository: RecipeRepository
): BaseUseCase<RecipeUpdateDomain, Int> {

    override suspend fun execute(params: RecipeUpdateDomain): Int {
        return recipeRepository.updateNameDescription(params)
    }
}