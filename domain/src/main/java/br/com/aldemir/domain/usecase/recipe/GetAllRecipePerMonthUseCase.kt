package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllRecipePerMonthUseCase constructor(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
) : BaseUseCase<GetAllRecipePerMonthUseCase.Params, List<RecipePerMonthDomain>> {

    override suspend fun execute(params: Params): List<RecipePerMonthDomain> {
        return recipePerMonthlyRepository.getAllRecipePerMonth(params.month, params.year)
    }

    data class Params(
        val month: String,
        val year: String
    )
}