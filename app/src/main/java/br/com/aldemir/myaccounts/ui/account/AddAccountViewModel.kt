package br.com.aldemir.myaccounts.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.aldemir.myaccounts.R
import br.com.aldemir.myaccounts.data.database.Account
import br.com.aldemir.myaccounts.ui.account.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class AddAccountViewModel(private val repository: AccountRepository) : ViewModel() {
    companion object {
        private const val TAG = "AddAccountFragment"
    }

    private val _addAccountFormState = MutableLiveData<AddAccountFormState>()
    val addAccountFormState: LiveData<AddAccountFormState> = _addAccountFormState

    private val _name = MutableStateFlow("")
    private val _value = MutableStateFlow("")
    private val _description = MutableStateFlow("")

    private val _id = MutableLiveData<Long>()
    val id: LiveData<Long> = _id

    fun insertAccount(account: Account) {
        _id.value = repository.insertAccount(account)
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_name, _value, _description) {
            name, value, description->
        val isNameCorrect = isNameValid(name)
        val isValueCorrect = isValueValid(value)
        val isDescriptionCorrect = isDescriptionValid(description)
        Log.w(TAG, "Buttom value: $value")
        Log.w(TAG, "Buttom isValueCorrect: $isValueCorrect")

        return@combine isNameCorrect and isValueCorrect and isDescriptionCorrect
    }

    private fun isNameValid(name: String): Boolean {
        return name.length > 4
    }

    private fun isValueValid(value: String): Boolean {
        return value.length > 3
    }

    private fun isDescriptionValid(description: String): Boolean {
        return description.length > 4
    }

    fun setName(name: String) {
        _name.value = name
        if (!isNameValid(name)) {
            _addAccountFormState.value = AddAccountFormState(nameError  = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddAccountFormState(nameError = null)
        }
    }

    fun setValue(value: String) {
        _value.value = value
        if (!isValueValid(value)) {
            _addAccountFormState.value = AddAccountFormState(valueError = R.string.invalid_value)
        } else {
            _addAccountFormState.value = AddAccountFormState(valueError = null)
        }
    }

    fun setDescription(description: String) {
        _description.value = description
        if (!isDescriptionValid(description)) {
            _addAccountFormState.value = AddAccountFormState(descriptionError = R.string.invalid_name)
        } else {
            _addAccountFormState.value = AddAccountFormState(descriptionError = null)
        }
    }

}