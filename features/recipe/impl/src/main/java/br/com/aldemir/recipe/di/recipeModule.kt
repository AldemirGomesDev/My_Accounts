package br.com.aldemir.recipe.di

import br.com.aldemir.recipe.presentation.addrecipe.AddRecipeViewModel
import br.com.aldemir.recipe.presentation.changerecipe.ChangeRecipeViewModel
import br.com.aldemir.recipe.presentation.detail.DetailRecipeViewModel
import br.com.aldemir.recipe.presentation.list.ListRecipeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val recipeModule = module {
    viewModel {
        AddRecipeViewModel(
            addRecipeUseCase = get(),
            addRecipeMonthlyUseCase = get()
        )
    }

    viewModel {
        ChangeRecipeViewModel(
            updateRecipeMonthlyUseCase = get(),
            getByIdRecipeMonthlyUseCase = get(),
            updateRecipeNameAndDescriptionUseCase = get(),
        )
    }

    viewModel {
        DetailRecipeViewModel(
            getAllByIdRecipeUseCase = get(),
            updateRecipeMonthlyUseCase = get()
        )
    }

    viewModel {
        ListRecipeViewModel(
            getAllRecipePerMonthUseCase = get(),
            getAllRecipeMonthlyUseCase = get(),
            deleteRecipeUseCase = get(),
        )
    }
}