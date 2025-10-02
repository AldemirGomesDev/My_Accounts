package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.common.util.DateUtils.getMonthByLanguage
import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetAllByIdRecipeUseCase(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : UseCase<Int, List<RecipePerMonthDomain>> {

    override suspend fun execute(params: Int): List<RecipePerMonthDomain> {
        val recipes = recipeMonthlyRepository.getAllByIdRecipe(params)
        return recipes.map { recipe ->
            recipe.copy(month = getMonthByLanguage(recipe.month))
        }
    }
}