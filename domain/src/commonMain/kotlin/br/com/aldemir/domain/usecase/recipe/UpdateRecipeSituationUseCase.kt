package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.repository.RecipeMonthlyRepository

class UpdateRecipeSituationUseCase(
    private val recipeMonthlyRepository: RecipeMonthlyRepository
): UseCase<UpdateRecipeSituationUseCase.Params, Int> {

    override suspend fun execute(params: Params): Int {
        return recipeMonthlyRepository.update(params.id, params.situation)
    }

    data class Params(
        val id: Int,
        val situation: Boolean
    )
}