package br.com.aldemir.publ.domain.recipe

import br.com.aldemir.data.database.model.RecipeDTO

interface GetAllRecipeUseCase {
    suspend operator fun invoke(): List<RecipeDTO>
}