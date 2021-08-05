package br.com.aldemir.myaccounts.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.AccountDao
import br.com.aldemir.myaccounts.ui.account.repository.AccountRepositoryImpl

class AddAccountViewModelFactory(private val accountDao: AccountDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAccountViewModel::class.java)) {
            return AddAccountViewModel(AccountRepositoryImpl(accountDao)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}