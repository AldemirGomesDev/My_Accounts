package br.com.aldemir.myaccounts.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.ExpenseDao
import br.com.aldemir.myaccounts.data.repository.ExpenseRepository
import br.com.aldemir.myaccounts.data.repository.ExpenseRepositoryImpl
import br.com.aldemir.myaccounts.domain.usecase.DeleteExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.DeleteExpenseUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllExpenseUseCaseImpl


class MainViewModelFactory(
    private val expenseRepository: ExpenseRepository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                DeleteExpenseUseCaseImpl(expenseRepository),
                GetAllExpenseUseCaseImpl(expenseRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}