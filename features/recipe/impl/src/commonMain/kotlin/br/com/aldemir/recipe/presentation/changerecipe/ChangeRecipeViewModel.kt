package br.com.aldemir.recipe.presentation.changerecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.util.emptyString
import br.com.aldemir.common.util.fromCurrency
import br.com.aldemir.common.util.toPercentString
import br.com.aldemir.domain.model.RecipeUpdateDomain
import br.com.aldemir.domain.usecase.recipe.GetByIdRecipeMonthlyUseCase
import br.com.aldemir.domain.usecase.recipe.UpdateRecipeMonthlyUseCase
import br.com.aldemir.recipe.mapper.toUiModel
import br.com.aldemir.recipe.presentation.changerecipe.action.ChangeRecipeAction
import br.com.aldemir.recipe.presentation.changerecipe.effect.ChangeRecipeEffect
import br.com.aldemir.recipe.presentation.changerecipe.model.ChangeRecipeUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangeRecipeViewModel(
    private val getByIdRecipeMonthlyUseCase: GetByIdRecipeMonthlyUseCase,
    private val updateRecipeMonthlyUseCase: UpdateRecipeMonthlyUseCase
) : ViewModel() {

    private val _uiModel = MutableStateFlow(ChangeRecipeUiModel())
    var uiModel = _uiModel.asStateFlow()

    private val _uiEffect = Channel<ChangeRecipeEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun handleAction(action: ChangeRecipeAction) {
        when (action) {
            is ChangeRecipeAction.OnTitleChange -> {
                _uiModel.update { it.copy(name = action.title) }
                validateName()
            }

            is ChangeRecipeAction.OnValueChange -> {
                _uiModel.update { it.copy(value = action.value.fromCurrency()) }
                validateValue()
            }

            is ChangeRecipeAction.OnDescriptionChange -> {
                _uiModel.update { it.copy(description = action.description) }
                validateDescription()
            }

            is ChangeRecipeAction.OnCheckedChange -> {
                _uiModel.update { it.copy(isCheckedPaid = action.isCheckedPaid) }
            }

            is ChangeRecipeAction.UpdateMonthlyRecipe -> {
                updateRecipeNameAndDescription(action.id, action.isPaid)
            }
        }
    }
    fun getAllByIdMonthlyRecipe(id: Int) = viewModelScope.launch {
        getByIdRecipeMonthlyUseCase(this, id) {
            success = { monthlyRecipe ->
                _uiModel.update {
                    monthlyRecipe.toUiModel()
                }
                validateFields()
            }
        }
    }

    private fun updateRecipeNameAndDescription(idMonthlyRecipe: Int, isPaid: Boolean) = viewModelScope.launch {
        val recipeUpdateDomain = RecipeUpdateDomain(
            id = _uiModel.value.recipeId,
            idMonthlyRecipe = idMonthlyRecipe,
            value = _uiModel.value.value,
            name = _uiModel.value.name,
            isPaid = isPaid,
            description = _uiModel.value.description
        )
        updateRecipeMonthlyUseCase(this, recipeUpdateDomain) {
            success = { id ->
                if (id > 0) {
                    sendEffect(ChangeRecipeEffect.NavigateToRecipeList)
                }
            }
        }
    }

    private fun sendEffect(effect: ChangeRecipeEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun validateFields() {
        validateName()
        validateValue()
        validateDescription()
    }

    private fun validateCheckedPaid(isPaid: Boolean) {
        _uiModel.update {
            it.copy(isCheckedPaid = _uiModel.value.isCheckedPaid)
        }
    }

    private fun validateName() {
        if (validateLength(_uiModel.value.name, 3)) {
            _uiModel.update {
                it.copy(isNameValid = true, nameError = "O nome deve conter no mínimo 3 dígitos")
            }
        } else {
            _uiModel.update {
                it.copy(isNameValid = false, nameError = emptyString())
            }
        }
        shouldEnabledRegisterButton()
    }

    private fun validateValue() {
        if (_uiModel.value.value == 0.0) {
            _uiModel.update {
                it.copy(isValueValid = true, valueError = "O valor é obrigatório")
            }
        } else {
            _uiModel.update {
                it.copy(isValueValid = false, valueError = emptyString())
            }
        }
        shouldEnabledRegisterButton()
    }

    private fun validateDescription() {
        if (validateLength(_uiModel.value.description, 2)) {
            _uiModel.update {
                it.copy(isDescriptionValid = true, descriptionError = "a descrição deve conter no mínimo 2 dígitos")
            }
        } else {
            _uiModel.update {
                it.copy(isDescriptionValid = false, descriptionError = emptyString())
            }
        }
        shouldEnabledRegisterButton()
    }

    private fun shouldEnabledRegisterButton() {
        _uiModel.update {
            it.copy(
                isEnabledRegisterButton = !validateLength(_uiModel.value.name, 3)
                        && _uiModel.value.value.toPercentString().isNotEmpty()
                        && !validateLength(_uiModel.value.value.toPercentString(), 2)
            )
        }
    }

    private fun validateLength(text: String, minLength: Int) = text.length < minLength
}