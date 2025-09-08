package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class AddRecipeMonthlyUseCase constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
) : BaseUseCase<RecipeMonthlyDomain, Long> {

    override suspend fun execute(params: RecipeMonthlyDomain): Long {
        return recipeMonthlyRepository.insert(params)
    }
}