package br.com.aldemir.home.presentation.effect

sealed class HomeUiEffect {
    data object ShowExpenseBarEmpty: HomeUiEffect()
    data object ShowRecipeBarEmpty: HomeUiEffect()
    data object Idle: HomeUiEffect()
}