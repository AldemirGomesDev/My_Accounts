package br.com.aldemir.domain.usecase.recipe

import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.base.awaitForResult
import br.com.aldemir.domain.model.RecipeUpdateDomain
import br.com.aldemir.domain.repository.RecipeMonthlyRepository
import br.com.aldemir.domain.repository.RecipeRepository

class UpdateRecipeMonthlyUseCase(
    private val recipeRepository: RecipeRepository,
    private val recipeMonthlyRepository: RecipeMonthlyRepository,
    private val updateRecipeSituationUseCase: UpdateRecipeSituationUseCase
): UseCase<RecipeUpdateDomain, Int> {

    override suspend fun execute(params: RecipeUpdateDomain): Int {
        val idMonthlyRecipe = recipeMonthlyRepository.updateValueById(params.idMonthlyRecipe, params.value)
        val idRecipe = recipeRepository.updateNameDescription(params)
        val situationUpdate = updateRecipeSituationUseCase.awaitForResult(
            UpdateRecipeSituationUseCase.Params(
                id = params.idMonthlyRecipe,
                situation = params.isPaid
            )
        )
        return checkUpdate(idMonthlyRecipe, idRecipe, situationUpdate)
    }

    private fun checkUpdate(idMonthlyRecipe: Int, idRecipe: Int, situationUpdate: Int): Int =
        if (idMonthlyRecipe > 0 && idRecipe > 0 && situationUpdate > 0) idRecipe else 0
}