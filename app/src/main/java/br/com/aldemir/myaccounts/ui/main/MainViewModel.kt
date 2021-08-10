package br.com.aldemir.myaccounts.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.domain.usecase.DeleteExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpenseUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getAllExpenseUseCase: GetAllExpenseUseCase
) : ViewModel() {
    private val _accounts = MutableLiveData<List<Expense>>()
    var accounts: LiveData<List<Expense>> = _accounts

    private val _id = MutableLiveData(0)
    val idAccount: LiveData<Int> = _id

    fun getAll() = viewModelScope.launch {
        _accounts.value = getAllExpenseUseCase()!!
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        _id.value = deleteExpenseUseCase(expense)
    }
    fun setId(id: Int) {
      _id.value = id
    }
}