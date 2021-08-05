package br.com.aldemir.myaccounts.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aldemir.myaccounts.data.database.Account
import br.com.aldemir.myaccounts.ui.account.repository.AccountRepository

class AddAccountViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _accounts = MutableLiveData<List<Account>>()
    var accounts: LiveData<List<Account>> = _accounts

    private val _id = MutableLiveData<Long>()
    val id: LiveData<Long> = _id

    fun insertAccount(account: Account) {
        _id.value = repository.insertAccount(account)
    }

    fun getAll() {
        accounts = repository.getAll()
    }
}