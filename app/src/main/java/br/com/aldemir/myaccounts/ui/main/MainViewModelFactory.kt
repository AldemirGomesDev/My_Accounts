package br.com.aldemir.myaccounts.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.ExpenseDao
import br.com.aldemir.myaccounts.data.repository.AccountRepositoryImpl


class MainViewModelFactory(private val expenseDao: ExpenseDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(AccountRepositoryImpl(expenseDao)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}