package br.com.aldemir.myaccounts.ui.historic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.domain.usecase.GetAllMonthlyPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricViewModel @Inject constructor (
    private val getAllMonthlyPaymentUseCase: GetAllMonthlyPaymentUseCase
    ) : ViewModel() {

    private val _monthlyPayment = MutableLiveData<List<MonthlyPayment>>()
    var monthlyPayment: LiveData<List<MonthlyPayment>> = _monthlyPayment

    fun getAllMonthlyPayment() = viewModelScope.launch {
        _monthlyPayment.value = getAllMonthlyPaymentUseCase()!!
    }
}