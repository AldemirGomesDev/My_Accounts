package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipePerMonthDTO

interface GetAllRecipePerMonthUseCase {
    suspend operator fun invoke(month: String, year: String): List<RecipePerMonthDTO>
}