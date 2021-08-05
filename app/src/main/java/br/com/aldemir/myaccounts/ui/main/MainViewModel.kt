package br.com.aldemir.myaccounts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aldemir.myaccounts.data.database.Account
import br.com.aldemir.myaccounts.ui.account.repository.AccountRepository

class MainViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _accounts = MutableLiveData<List<Account>>()
    var accounts: LiveData<List<Account>> = _accounts

    fun getAll() {
        accounts = repository.getAll()
    }
}