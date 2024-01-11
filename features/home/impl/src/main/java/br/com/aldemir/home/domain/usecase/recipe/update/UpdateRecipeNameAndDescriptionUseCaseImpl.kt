package br.com.aldemir.home.domain.usecase.recipe.update

import br.com.aldemir.publ.domain.recipe.UpdateRecipeNameAndDescriptionUseCase
import br.com.aldemir.data.repository.recipe.RecipeRepository

class UpdateRecipeNameAndDescriptionUseCaseImpl constructor(
    private val recipeRepository: RecipeRepository
): UpdateRecipeNameAndDescriptionUseCase {
    override suspend fun invoke(recipeUpdateDTO: br.com.aldemir.data.database.model.RecipeUpdateDTO): Int {
        return recipeRepository.updateNameDescription(recipeUpdateDTO)
    }
}