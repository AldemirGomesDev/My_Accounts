package br.com.aldemir.recipe.presentation.changerecipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.fromCurrency
import br.com.aldemir.common.util.pointString
import br.com.aldemir.common.util.zeroString
import br.com.aldemir.domain.model.RecipePerMonthDomain
import br.com.aldemir.domain.model.RecipeUpdateDomain
import br.com.aldemir.domain.usecase.recipe.GetByIdRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeNameAndDescriptionUseCase
import br.com.aldemir.recipe.mapper.toDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangeRecipeViewModel constructor(
    private val updateRecipeMonthlyUseCase: UpdateRecipeMonthlyUseCase,
    private val getByIdRecipeMonthlyUseCase: GetByIdRecipeMonthlyUseCase,
    private val updateRecipeNameAndDescriptionUseCase: UpdateRecipeNameAndDescriptionUseCase
) : ViewModel() {

    val recipeId: MutableState<Int> = mutableStateOf(0)

    val value: MutableState<String> = mutableStateOf(emptyString())
    val isValueValid: MutableState<Boolean> = mutableStateOf(false)
    val valueError: MutableState<String> = mutableStateOf(emptyString())

    val name: MutableState<String> = mutableStateOf(emptyString())
    val isNameValid: MutableState<Boolean> = mutableStateOf(false)
    val nameError: MutableState<String> = mutableStateOf(emptyString())

    val description: MutableState<String> = mutableStateOf(emptyString())
    val isDescriptionValid: MutableState<Boolean> = mutableStateOf(false)
    val descriptionError: MutableState<String> = mutableStateOf(emptyString())

    val isCheckedPaid: MutableState<Boolean> = mutableStateOf(false)

    var isEnabledRegisterButton: MutableState<Boolean> = mutableStateOf(true)

    private val _idMonthlyRecipe = MutableStateFlow(0)
    val idMonthlyRecipe = _idMonthlyRecipe.asStateFlow()

    private val _recipeMonthlyView = MutableStateFlow(RecipePerMonthDomain())
    var recipeMonthlyView: StateFlow<RecipePerMonthDomain> = _recipeMonthlyView

    fun getAllByIdMonthlyRecipe(id: Int) = viewModelScope.launch {
        getByIdRecipeMonthlyUseCase(this, id).apply {
            onSuccess { monthlyRecipe ->
                _recipeMonthlyView.value = monthlyRecipe
            }
        }
    }

    fun updateMonthlyRecipe() = viewModelScope.launch {
        _recipeMonthlyView.value.value = value.value.fromCurrency()
        _recipeMonthlyView.value.status = isCheckedPaid.value
        updateRecipeMonthlyUseCase(this, _recipeMonthlyView.value.toDatabase()).apply {
            onSuccess {
                _idMonthlyRecipe.value = it
            }
        }
        updateRecipeNameAndDescription()
    }

    private fun updateRecipeNameAndDescription() = viewModelScope.launch {
        val recipeUpdateDomain = RecipeUpdateDomain(
            id = recipeId.value,
            name = name.value,
            description = description.value
        )
        updateRecipeNameAndDescriptionUseCase(this, recipeUpdateDomain)
    }

    fun getValueWithTwoDecimal(value: String): String {
        val newValue = if (verifyTwoCharactersAfterPoint(value)) "$value${zeroString()}"
        else removePointString(value)
        return removePointString(newValue)
    }

    private fun removePointString(value: String): String {
        return value.replace(pointString(), emptyString())
    }

    private fun verifyTwoCharactersAfterPoint(value: String): Boolean {
        val string = value.split(pointString())
        return string[1].length == 1
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

    private fun shouldEnabledRegisterButton() {
        isEnabledRegisterButton.value = !validateLength(name.value, 3)
                && value.value.isNotEmpty()
                && !validateLength(description.value, 2)
    }

    private fun validateLength(text: String, minLength: Int) = text.length < minLength
}