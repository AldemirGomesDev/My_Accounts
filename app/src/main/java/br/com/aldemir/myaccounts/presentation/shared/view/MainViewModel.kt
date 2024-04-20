package br.com.aldemir.myaccounts.presentation.shared.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.domain.usecase.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.expense.presentation.listexpense.state.MainUiState
import br.com.aldemir.myaccounts.presentation.shared.action.MainAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveDarkModeStateUseCase: SaveDarkModeStateUseCase,
    private val readDarkModeStateUseCase: ReadDarkModeStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    fun onAction(action: MainAction) {
        when (action) {
            MainAction.FetchData -> readDarkModeState()
            is MainAction.UpdateDarkModeState -> updateDarkModeState(action.isDarkMode)
        }
    }

    private fun updateDarkModeState(isDarkMode: Boolean) {
        _uiState.value = _uiState.value.copy(darkMode = isDarkMode)
        saveDarkModeState(isDarkMode)
    }

    private fun saveDarkModeState(isDarkMode: Boolean) {
        viewModelScope.launch {
            saveDarkModeStateUseCase(this, isDarkMode)
            readDarkModeState()
        }
    }

    private fun readDarkModeState() {
        try {
            viewModelScope.launch {
                readDarkModeStateUseCase(this, Unit).apply {
                    onSuccess {
                        it.collect { isDark ->
                            _uiState.value = _uiState.value.copy(darkMode = isDark)
                        }
                    }
                    onFailure {
                        _uiState.value = _uiState.value.copy(darkMode = false)
                    }
                }

            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(darkMode = false)
        }
    }
}