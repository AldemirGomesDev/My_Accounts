package br.com.aldemir.myaccounts.presentation.home.state

sealed class ButtonType {
    object ButtonRecipe: ButtonType()
    object ButtonExpense: ButtonType()
}
