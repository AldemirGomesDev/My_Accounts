package br.com.aldemir.myaccounts.presentation.addexpense

data class AddExpenseFormState(
    val nameError: Int? = null,
    val valueError: Int? = null,
    val descriptionError: Int? = null,
    val yearError: Int? = null,
    val monthsError: Int? = null
)
