package br.com.aldemir.myaccounts.ui.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.ExpenseDao
import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.repository.AccountRepositoryImpl
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl

class AddAccountViewModelFactory(
    private val expenseDao: ExpenseDao,
    private val monthlyPaymentDao: MonthlyPaymentDao
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAccountViewModel::class.java)) {
            return AddAccountViewModel(
                AccountRepositoryImpl(expenseDao),
                MonthlyPaymentRepositoryImpl(monthlyPaymentDao)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}