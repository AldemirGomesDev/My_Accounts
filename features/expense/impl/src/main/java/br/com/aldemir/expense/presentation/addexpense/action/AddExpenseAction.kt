package br.com.aldemir.expense.presentation.addexpense.action

sealed class AddExpenseAction {
    data class NameChanged(val name: String): AddExpenseAction()
    data class ValueChanged(val value: String): AddExpenseAction()
    data class DescriptionChanged(val description: String): AddExpenseAction()
    data class CheckedPaidChanged(val checked: Boolean): AddExpenseAction()
    data class AccountRepeatChanged(val checked: Boolean): AddExpenseAction()
    data class DueDateSelectedChanged(val dueDate: String): AddExpenseAction()
    data class AmountThatRepeatsSelectedChanged(val amountThatRepeatsSelected: String): AddExpenseAction()
    object Submit: AddExpenseAction()
    object ClearForm: AddExpenseAction()
}