package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class GetAllRecipeUseCase constructor(
    private val recipeRepository: RecipeRepository
) : BaseUseCase<Unit, List<RecipeDomain>> {

    override suspend fun execute(params: Unit): List<RecipeDomain> {
        return recipeRepository.getAll()
    }
}