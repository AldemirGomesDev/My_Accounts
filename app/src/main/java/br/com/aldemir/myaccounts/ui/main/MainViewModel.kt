package br.com.aldemir.myaccounts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aldemir.myaccounts.data.domain.model.Expense
import br.com.aldemir.myaccounts.data.repository.AccountRepository

class MainViewModel(private val repository: AccountRepository) : ViewModel() {
    private val _accounts = MutableLiveData<List<Expense>>()
    var accounts: LiveData<List<Expense>> = _accounts

    private val _id = MutableLiveData(0)
    val idAccount: LiveData<Int> = _id

    fun getAll() {
        accounts = repository.getAll()
    }

    fun delete(expense: Expense) {
        _id.value = repository.delete(expense)
    }
    fun setId(id: Int) {
      _id.value = id
    }
}