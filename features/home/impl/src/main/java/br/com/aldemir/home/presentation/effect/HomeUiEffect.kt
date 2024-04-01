package br.com.aldemir.home.presentation.effect

sealed class HomeUiEffect {
    object ShowExpenseBarEmpty: HomeUiEffect()
    object ShowRecipeBarEmpty: HomeUiEffect()
    object Idle: HomeUiEffect()
}