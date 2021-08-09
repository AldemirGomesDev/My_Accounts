package br.com.aldemir.myaccounts.ui.expensedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.data.repository.MonthlyPaymentRepository

class ExpenseDetailViewModel(
    private val monthlyPaymentRepository: MonthlyPaymentRepository
    ) : ViewModel()  {

    companion object {
        private const val TAG = "ExpenseDetailFragment"
    }

    private val _monthlyPayment = MutableLiveData<List<MonthlyPayment>>()
    var monthlyPayment: LiveData<List<MonthlyPayment>> = _monthlyPayment

    fun getAllByIdExpense(id: Int) {
        monthlyPayment = monthlyPaymentRepository.getAllByIdExpense(id)
    }

    fun updateMonthlyPayment(monthlyPayment: MonthlyPayment) {
        monthlyPaymentRepository.update(monthlyPayment)
    }
}