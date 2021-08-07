package br.com.aldemir.myaccounts.ui.expensedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl

class ExpenseDetailViewModelFactory(
    private val monthlyPaymentDao: MonthlyPaymentDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseDetailViewModel::class.java)) {
            return ExpenseDetailViewModel(
                MonthlyPaymentRepositoryImpl(monthlyPaymentDao)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}