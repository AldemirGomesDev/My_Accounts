package br.com.aldemir.home.presentation.model

sealed class ButtonType {
    data object ButtonRecipe: ButtonType()
    data object ButtonExpense: ButtonType()
}
