package br.com.aldemir.myaccounts.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.ExpenseDao
import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.data.repository.ExpenseRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl
import br.com.aldemir.myaccounts.domain.usecase.AddExpenseUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.AddMonthlyPaymentUseCaseImpl

class AddAccountViewModelFactory(
    private val expenseRepository: ExpenseRepository,
    private val monthlyPaymentRepository: MonthlyPaymentRepository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAccountViewModel::class.java)) {
            return AddAccountViewModel(
                AddExpenseUseCaseImpl(expenseRepository),
                AddMonthlyPaymentUseCaseImpl(monthlyPaymentRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}