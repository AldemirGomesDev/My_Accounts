package br.com.aldemir.recipe.presentation.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.util.DateUtils
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.fromCurrency
import br.com.aldemir.domain.model.RecipeDomain
import br.com.aldemir.domain.model.RecipeMonthlyDomain
import br.com.aldemir.domain.usecase.recipe.AddRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.AddRecipeUseCase
import br.com.aldemir.recipe.presentation.addrecipe.action.AddRecipeAction
import br.com.aldemir.recipe.presentation.addrecipe.effect.AddRecipeEffect
import br.com.aldemir.recipe.presentation.addrecipe.model.AddRecipeUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddRecipeViewModel(
    private val addRecipeUseCase: AddRecipeUseCase,
    private val addRecipeMonthlyUseCase: AddRecipeMonthlyUseCase
) : ViewModel() {

    private val _uiModel = MutableStateFlow(AddRecipeUiModel())
    val uiModel = _uiModel.asStateFlow()

    private val _uiEffect = Channel<AddRecipeEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun handleAction(action: AddRecipeAction) {
        when (action) {
            is AddRecipeAction.SetName -> {
                _uiModel.update { it.copy(name = action.name) }
                validateName()
            }
            is AddRecipeAction.SetValue -> {
                _uiModel.update { it.copy(value = action.value) }
                validateValue()
            }
            is AddRecipeAction.SetDescription -> {
                _uiModel.update { it.copy(description = action.description) }
                validateDescription()
            }
            is AddRecipeAction.SetIsCheckedPaid -> {
                _uiModel.update { it.copy(isCheckedPaid = action.isCheckedPaid) }
            }
            is AddRecipeAction.SetIsAccountRepeat -> {
                _uiModel.update { it.copy(isAccountRepeat = action.isAccountRepeat) }
                clearRepeatAmount(action.isAccountRepeat)
            }
            is AddRecipeAction.SetAmountThatRepeatsSelected -> {
                _uiModel.update { it.copy(amountThatRepeatsSelected = action.amountThatRepeatsSelected) }
            }
            is AddRecipeAction.SetDueDateSelected -> {
                _uiModel.update { it.copy(dueDateSelected = action.dueDateSelected) }
            }
            is AddRecipeAction.SaveRecipe -> {
                saveAccount()
            }
        }
    }
    private fun saveAccount() = viewModelScope.launch {
        val recipeDomain = RecipeDomain(
            name = _uiModel.value.name,
            description = _uiModel.value.description,
            created_at = DateUtils.getCurrentDate(),
            due_date = _uiModel.value.dueDateSelected
        )
        addRecipeUseCase(this, recipeDomain) {
            success =  { recipeId ->
                handleMonthlyPayment(recipeId)
            }
        }
    }

    private fun handleMonthlyPayment(recipeId: Long) {
        _uiModel.update {
            it.copy(recipeId = recipeId.toInt())
        }
        val years = DateUtils.getYears(_uiModel.value.amountThatRepeatsSelected)
        val months = DateUtils.getMonths(_uiModel.value.amountThatRepeatsSelected)

        for ((index, month) in months.withIndex()){
            val recipeMonthlyDomain = RecipeMonthlyDomain(
                id_recipe = recipeId.toInt(),
                year = years[index],
                month = month,
                value = _uiModel.value.value.fromCurrency(),
                status = if (index == 0) _uiModel.value.isCheckedPaid else false
            )
            insertMonthlyPayment(recipeMonthlyDomain)
        }
    }

    private fun insertMonthlyPayment(recipeMonthlyDomain: RecipeMonthlyDomain) = viewModelScope.launch {
        addRecipeMonthlyUseCase(this, recipeMonthlyDomain) {
            success = {
                viewModelScope.launch {
                    _uiEffect.send(AddRecipeEffect.RecipeSaved)
                }
            }
        }
    }

    private fun shouldEnabledRegisterButton() {
        _uiModel.value.isEnabledRegisterButton = !validateLength(_uiModel.value.name, 3)
                && _uiModel.value.value.isNotEmpty()
                && !validateLength(_uiModel.value.description, 2)
    }

    private fun validateLength(text: String, minLength: Int) = text.length < minLength

    private fun clearRepeatAmount(isChecked: Boolean) {
        if (!isChecked) {
            _uiModel.update {
                it.copy(
                    amountThatRepeatsSelected = 1
                )
            }
        }
    }

    private fun validateName() {
        if (validateLength(_uiModel.value.name, 3)) {
            _uiModel.update {
                it.copy(
                    isNameValid = true,
                    nameError = "O nome deve conter no mínimo 3 dígitos"
                )
            }
        } else {
            _uiModel.update {
                it.copy(
                    isNameValid = false,
                    nameError = emptyString()
                )
            }
        }
        shouldEnabledRegisterButton()
    }

    private fun validateValue() {
        if (_uiModel.value.value.isEmpty()) {
            _uiModel.update {
                it.copy(
                    isValueValid = true,
                    valueError = "O valor é obrigatório"
                )
            }
        } else {
            _uiModel.update {
                it.copy(
                    isValueValid = false,
                    valueError = emptyString()
                )
            }
        }
        shouldEnabledRegisterButton()
    }

    private fun validateDescription() {
        if (validateLength(_uiModel.value.description, 2)) {
            _uiModel.update {
                it.copy(
                    isDescriptionValid = true,
                    descriptionError = "A descrição deve conter no mínimo 2 dígitos"
                )
            }
        } else {
            _uiModel.update {
                it.copy(
                    isDescriptionValid = false,
                    descriptionError = emptyString()
                )
            }
        }
        shouldEnabledRegisterButton()
    }
}