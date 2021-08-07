package br.com.aldemir.myaccounts.data.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.data.domain.model.Expense

interface AccountRepository {
    fun insertAccount(expense: Expense): Long
    fun update(expense: Expense): Int
    fun delete(expense: Expense): Int
    fun getAll(): LiveData<List<Expense>>
}