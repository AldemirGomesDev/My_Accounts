package br.com.aldemir.expense.di

import br.com.aldemir.expense.presentation.addexpense.AddExpenseViewModel
import br.com.aldemir.expense.presentation.expensechange.ChangeExpenseViewModel
import br.com.aldemir.expense.presentation.expensedetail.ExpenseDetailViewModel
import br.com.aldemir.expense.presentation.historic.HistoricViewModel
import br.com.aldemir.expense.presentation.listexpense.ListExpenseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val expenseModule = module {
    viewModelOf(::AddExpenseViewModel)
    viewModelOf(::ChangeExpenseViewModel)
    viewModelOf(::ExpenseDetailViewModel)
    viewModelOf(::HistoricViewModel)
    viewModelOf(::ListExpenseViewModel)
}