package br.com.aldemir.expense.di

import br.com.aldemir.expense.presentation.addexpense.AddExpenseViewModel
import br.com.aldemir.expense.presentation.expensechange.ChangeExpenseViewModel
import br.com.aldemir.expense.presentation.expensedetail.ExpenseDetailViewModel
import br.com.aldemir.expense.presentation.historic.HistoricViewModel
import br.com.aldemir.expense.presentation.listexpense.ListExpenseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val expenseModule = module {
    viewModel {
        AddExpenseViewModel(
            addExpenseUseCase = get(),
            addMonthlyPaymentUseCase = get()
        )
    }

    viewModel {
        ChangeExpenseViewModel(
            updateMonthlyPaymentUseCase = get(),
            getByIdMonthlyPaymentUseCase = get()
        )
    }

    viewModel {
        ExpenseDetailViewModel(
            updateMonthlyPaymentUseCase = get(),
            getAllByIdExpenseUseCase = get()
        )
    }

    viewModel {
        HistoricViewModel(
            getAllMonthlyPaymentUseCase = get(),
            getAllExpensesMonthUseCase = get(),
            getAllExpensePerMonthUseCase = get(),
        )
    }

    viewModel {
        ListExpenseViewModel(
            deleteExpenseUseCase = get(),
            getAllExpensesMonthUseCase = get(),
            getAllExpensePerMonthUseCase = get(),
            saveDarkModeStateUseCase = get(),
            readDarkModeStateUseCase = get(),
        )
    }
}