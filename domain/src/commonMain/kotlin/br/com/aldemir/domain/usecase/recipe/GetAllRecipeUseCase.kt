package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.repository.RecipeRepository

class GetAllRecipeUseCase(
    private val recipeRepository: RecipeRepository
) : UseCase<Unit, List<RecipeDomain>> {

    override suspend fun execute(params: Unit): List<RecipeDomain> {
        return recipeRepository.getAll()
    }
}