package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class GetByIdRecipeMonthlyUseCase(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): UseCase<Int, RecipePerMonthDomain> {

    override suspend fun execute(params: Int): RecipePerMonthDomain {
        return recipeMonthlyRepository.getByIdRecipeMonthly(params)
    }

}