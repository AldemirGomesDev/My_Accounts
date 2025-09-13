package br.com.aldemir.expense.presentation.addexpense.state

import br.com.aldemir.common.util.emptyString

data class AddExpenseUiState(
    val name: String = emptyString(),
    val isNameValid: Boolean = false,
    val nameError: String = emptyString(),
    val value: String = emptyString(),
    val isValueValid: Boolean = false,
    val valueError: String = emptyString(),
    val description: String = emptyString(),
    val isDescriptionValid: Boolean = false,
    val descriptionError: String = emptyString(),
    val isCheckedPaid: Boolean = false,
    val isAccountRepeat: Boolean = false,
    val amountThatRepeatsSelected: Int = 1,
    val dueDateSelected: Int = 1,
    var isEnabledRegisterButton: Boolean = false,
)