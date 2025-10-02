package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.common.util.DateUtils.getMonthByLanguage
import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllRecipeMonthUseCase(
    private val recipePerMonthlyRepository: RecipeMonthlyRepository
): UseCase<GetAllRecipeMonthUseCase.Params, List<RecipeMonthlyDomain>> {

    override suspend fun execute(params: Params): List<RecipeMonthlyDomain> {
        val recipes = recipePerMonthlyRepository.getAllRecipeMonth(params.month, params.year)
        return recipes.map { recipe ->
            recipe.copy(month = getMonthByLanguage(recipe.month))
        }
    }

    data class Params(
        val month: String,
        val year: String
    )
}