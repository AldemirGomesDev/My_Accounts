package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllByIdRecipeUseCase constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : BaseUseCase<Int, List<RecipePerMonthDomain>> {

    override suspend fun execute(params: Int): List<RecipePerMonthDomain> {
        return recipeMonthlyRepository.getAllByIdRecipe(params)
    }
}