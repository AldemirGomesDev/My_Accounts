package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeMonthlyDomain

interface GetAllByIdRecipeUseCase {
    suspend operator fun invoke(id: Int): List<RecipeMonthlyDomain>
}