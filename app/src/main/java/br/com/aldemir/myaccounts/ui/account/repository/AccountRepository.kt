package br.com.aldemir.myaccounts.ui.account.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.data.database.Account

interface AccountRepository {
    fun insertAccount(account: Account): Long
    fun update(account: Account): Int
    fun delete(account: Account): Int
    fun getAll(): LiveData<List<Account>>
}