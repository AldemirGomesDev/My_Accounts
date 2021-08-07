package br.com.aldemir.myaccounts.ui.expense

data class AddAccountFormState(
    val nameError: Int? = null,
    val valueError: Int? = null,
    val descriptionError: Int? = null,
    val yearError: Int? = null,
    val monthsError: Int? = null
)
