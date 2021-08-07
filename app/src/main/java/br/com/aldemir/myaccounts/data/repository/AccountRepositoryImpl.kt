package br.com.aldemir.myaccounts.data.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.data.domain.model.Expense
import br.com.aldemir.myaccounts.data.database.ExpenseDao

class AccountRepositoryImpl(private val expenseDao: ExpenseDao): AccountRepository {
    override fun insertAccount(expense: Expense): Long {
        return expenseDao.insert(expense)
    }

    override fun update(expense: Expense): Int {
        return expenseDao.update(expense)
    }

    override fun delete(expense: Expense): Int {
        return expenseDao.delete(expense)
    }

    override fun getAll(): LiveData<List<Expense>> {
        return expenseDao.getAll()
    }
}