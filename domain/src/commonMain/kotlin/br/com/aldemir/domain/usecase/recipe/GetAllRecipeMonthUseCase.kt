package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllRecipeMonthUseCase(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
): UseCase<GetAllRecipeMonthUseCase.Params, List<RecipeMonthlyDomain>> {

    override suspend fun execute(params: Params): List<RecipeMonthlyDomain> {
        return recipePerMonthlyRepository.getAllRecipeMonth(params.month, params.year)
    }

    data class Params(
        val month: String,
        val year: String
    )
}