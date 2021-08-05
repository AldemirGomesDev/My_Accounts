package br.com.aldemir.myaccounts.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.AccountDao
import br.com.aldemir.myaccounts.ui.account.repository.AccountRepositoryImpl


class MainViewModelFactory(private val accountDao: AccountDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(AccountRepositoryImpl(accountDao)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}