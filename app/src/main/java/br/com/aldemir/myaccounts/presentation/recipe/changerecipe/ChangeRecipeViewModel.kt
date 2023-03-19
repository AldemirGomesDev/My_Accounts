package br.com.aldemir.myaccounts.presentation.recipe.changerecipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.domain.mapper.toDatabase
import br.com.aldemir.myaccounts.domain.model.RecipePerMonth
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetAllByIdRecipeUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.getrecipemonthly.GetByIdRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.domain.usecase.recipe.update.UpdateRecipeMonthlyUseCase
import br.com.aldemir.myaccounts.presentation.shared.model.RecipeMonthlyView
import br.com.aldemir.myaccounts.util.emptyString
import br.com.aldemir.myaccounts.util.fromCurrency
import br.com.aldemir.myaccounts.util.pointString
import br.com.aldemir.myaccounts.util.zeroString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeRecipeViewModel @Inject constructor(
    private val updateRecipeMonthlyUseCase: UpdateRecipeMonthlyUseCase,
    private val getByIdRecipeMonthlyUseCase: GetByIdRecipeMonthlyUseCase,
) : ViewModel() {

    val value: MutableState<String> = mutableStateOf(emptyString())

    private val _monthlyRecipe = MutableStateFlow(RecipeMonthly())
    var monthlyRecipe: StateFlow<RecipeMonthly> = _monthlyRecipe

    private val _idMonthlyRecipe = MutableStateFlow(0)
    val idMonthlyRecipe = _idMonthlyRecipe.asStateFlow()

    private val _recipeMonthlyView = MutableStateFlow(RecipePerMonth())
    var recipeMonthlyView: StateFlow<RecipePerMonth> = _recipeMonthlyView

    fun getAllByIdMonthlyRecipe(id: Int) = viewModelScope.launch {
        val monthlyRecipe = getByIdRecipeMonthlyUseCase(id)
        _recipeMonthlyView.value = monthlyRecipe
    }

    fun updateMonthlyRecipe() = viewModelScope.launch {
        _recipeMonthlyView.value.value = value.value.fromCurrency()
        _idMonthlyRecipe.value = updateRecipeMonthlyUseCase(_recipeMonthlyView.value.toDatabase())
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
}