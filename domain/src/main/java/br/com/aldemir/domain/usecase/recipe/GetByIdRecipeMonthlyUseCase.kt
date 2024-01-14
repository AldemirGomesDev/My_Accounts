package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.BaseUseCase
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetByIdRecipeMonthlyUseCase constructor(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): BaseUseCase<Int, RecipePerMonthDomain> {

    override suspend fun execute(params: Int): RecipePerMonthDomain {
        return recipeMonthlyRepository.getByIdRecipeMonthly(params)
    }

}