package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class UpdateRecipeMonthlyUseCase constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): BaseUseCase<RecipeMonthlyDomain, Int> {

    override suspend fun execute(params: RecipeMonthlyDomain): Int {
        return recipeMonthlyRepository.update(params)
    }
}