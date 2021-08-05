package br.com.aldemir.myaccounts.ui.account.repository

import androidx.lifecycle.LiveData
import br.com.aldemir.myaccounts.data.database.Account
import br.com.aldemir.myaccounts.data.database.AccountDao

class AccountRepositoryImpl(private val accountDao: AccountDao): AccountRepository {
    override fun insertAccount(account: Account): Long {
        return accountDao.insert(account)
    }

    override fun update(account: Account): Int {
        return accountDao.update(account)
    }

    override fun getAll(): LiveData<List<Account>> {
        return accountDao.getAll()
    }
}