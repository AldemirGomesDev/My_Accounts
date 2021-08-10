package br.com.aldemir.myaccounts.ui.expensechange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.aldemir.myaccounts.data.database.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepositoryImpl
import br.com.aldemir.myaccounts.domain.usecase.GetByIdMonthlyPaymentUseCaseImpl
import br.com.aldemir.myaccounts.domain.usecase.UpdateMonthlyPaymentUseCaseImpl


class ChangeExpenseViewModelFactory(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeExpenseViewModel::class.java)) {
            return ChangeExpenseViewModel(
                UpdateMonthlyPaymentUseCaseImpl(monthlyPaymentRepository),
                GetByIdMonthlyPaymentUseCaseImpl(monthlyPaymentRepository)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}