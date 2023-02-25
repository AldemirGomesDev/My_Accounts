package br.com.aldemir.myaccounts.presentation.recipe.addrecipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.domain.usecase.recipe.AddRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.AddRecipeUseCase
import br.com.aldemir.myaccounts.util.Const.TAG
import br.com.aldemir.myaccounts.util.DateUtils
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.fromCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val addRecipeMonthlyUseCase: AddRecipeMonthlyUseCase
) : ViewModel() {

    val id: MutableState<Int> = mutableStateOf(0)

    val name: MutableState<String> = mutableStateOf(emptyString())
    val isNameValid: MutableState<Boolean> = mutableStateOf(false)
    val nameError: MutableState<String> = mutableStateOf(emptyString())

    val value: MutableState<String> = mutableStateOf(emptyString())
    val isValueValid: MutableState<Boolean> = mutableStateOf(false)
    val valueError: MutableState<String> = mutableStateOf(emptyString())

    val description: MutableState<String> = mutableStateOf(emptyString())
    val isDescriptionValid: MutableState<Boolean> = mutableStateOf(false)
    val descriptionError: MutableState<String> = mutableStateOf(emptyString())

    val isCheckedPaid: MutableState<Boolean> = mutableStateOf(false)
    val isAccountRepeat: MutableState<Boolean> = mutableStateOf(false)

    val amountThatRepeatsSelected: MutableState<Int> = mutableStateOf(1)
    val dueDateSelected: MutableState<Int> = mutableStateOf(1)

    var isEnabledRegisterButton: MutableState<Boolean> = mutableStateOf(false)

    fun saveAccount() = viewModelScope.launch {
        val recipe = Recipe(
            name = name.value,
            description = description.value,
            created_at = DateUtils.getDate(),
            due_date = dueDateSelected.value
        )
        val idExpense = addRecipeUseCase(recipe)
        id.value = idExpense.toInt()
        val years = DateUtils.getYears(amountThatRepeatsSelected.value)
        val months = DateUtils.getMonths(amountThatRepeatsSelected.value)

        for ((index, month) in months.withIndex()){
            val recipeMonthly = RecipeMonthly(
                id_expense = idExpense.toInt(),
                year = years[index],
                month = month,
                value = value.value.fromCurrency(),
                status = if (index == 0) isCheckedPaid.value else false
            )
            insertMonthlyPayment(recipeMonthly)
        }
    }

    private fun insertMonthlyPayment(recipeMonthly: RecipeMonthly) = viewModelScope.launch {
        val idMonthlyPayment = addRecipeMonthlyUseCase(recipeMonthly)
    }

    private fun shouldEnabledRegisterButton() {
        isEnabledRegisterButton.value = !validateLength(name.value, 3)
                && value.value.isNotEmpty()
                && !validateLength(description.value, 2)
    }

    private fun validateLength(text: String, minLength: Int) = text.length < minLength

    fun clearRepeatAmount(isChecked: Boolean) {
        if (!isChecked) amountThatRepeatsSelected.value = 1
    }

    fun validateName() {
        if (validateLength(name.value, 3)) {
            isNameValid.value = true
            nameError.value = "O nome deve conter no mínimo 3 dígitos"
        } else {
            isNameValid.value = false
            nameError.value = emptyString()
        }
        shouldEnabledRegisterButton()
    }

    fun validateValue() {
        if (value.value.isEmpty()) {
            isValueValid.value = true
            valueError.value = "O valor é obrigatório"
        } else {
            isValueValid.value = false
            valueError.value = emptyString()
        }
        shouldEnabledRegisterButton()
    }

    fun validateDescription() {
        if (validateLength(description.value, 2)) {
            isDescriptionValid.value = true
            descriptionError.value = "a descrição deve conter no mínimo 2 dígitos"
        } else {
            isDescriptionValid.value = false
            descriptionError.value = emptyString()
        }
        shouldEnabledRegisterButton()
    }

    fun getNumberOfTimesItRepeats(): MutableList<String> {
        Log.w(TAG, "getNumberOfTimesItRepeats: ")

        val numberOfTimesItRepeats = arrayListOf<String>()
        for (i in 1..100) {
            numberOfTimesItRepeats.add(i.toString())
        }
        return numberOfTimesItRepeats
    }
}