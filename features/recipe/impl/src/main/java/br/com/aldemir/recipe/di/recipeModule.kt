package br.com.aldemir.recipe.di

import br.com.aldemir.recipe.presentation.addrecipe.AddRecipeViewModel
import br.com.aldemir.recipe.presentation.changerecipe.ChangeRecipeViewModel
import br.com.aldemir.recipe.presentation.detail.DetailRecipeViewModel
import br.com.aldemir.recipe.presentation.list.ListRecipeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recipeModule = module {
    viewModelOf(::AddRecipeViewModel)
    viewModelOf(::ChangeRecipeViewModel)
    viewModelOf(::DetailRecipeViewModel)
    viewModelOf(::ListRecipeViewModel)
}