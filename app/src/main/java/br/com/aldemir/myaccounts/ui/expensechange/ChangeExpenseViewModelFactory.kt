package br.com.aldemir.myaccounts.ui.expensechange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl


class ChangeExpenseViewModelFactory(
    private val monthlyPaymentDao: MonthlyPaymentDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeExpenseViewModel::class.java)) {
            return ChangeExpenseViewModel(
                MonthlyPaymentRepositoryImpl(monthlyPaymentDao)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}