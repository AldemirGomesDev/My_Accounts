package br.com.aldemir.myaccounts.ui.expensedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl
import br.com.aldemir.myaccounts.domain.usecase.GetAllByIdExpenseUseCase
import br.com.aldemir.myaccounts.domain.usecase.GetAllByIdExpenseUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.UpdateMonthlyPaymentUseCase
import br.com.aldemir.myaccounts.domain.usecase.UpdateMonthlyPaymentUseCaseImpl

class ExpenseDetailViewModelFactory(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseDetailViewModel::class.java)) {
            return ExpenseDetailViewModel(
                UpdateMonthlyPaymentUseCaseImpl(monthlyPaymentRepository),
                GetAllByIdExpenseUseCaseImpl(monthlyPaymentRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}