package br.com.aldemir.home.presentation.model

sealed class ButtonType {
    object ButtonRecipe: ButtonType()
    object ButtonExpense: ButtonType()
}
