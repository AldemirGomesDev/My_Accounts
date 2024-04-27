package br.com.aldemir.myaccounts.presentation.shared.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.R
import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.common.theme.DarkModeDropDownState
import br.com.aldemir.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.domain.usecase.darkmode.SaveDarkModeStateUseCase
import br.com.aldemir.expense.presentation.listexpense.state.MainUiState
import br.com.aldemir.myaccounts.presentation.shared.action.MainAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveDarkModeStateUseCase: SaveDarkModeStateUseCase,
    private val readDarkModeStateUseCase: ReadDarkModeStateUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    fun onAction(action: MainAction) {
        when (action) {
            MainAction.FetchData -> {
                handleDarkModeMenu()
                readDarkModeState()
            }
            is MainAction.UpdateDarkModeState -> saveDarkModeState(action.appDarkMode)
        }
    }

    private fun saveDarkModeState(appDarkMode: AppDarkMode) {
        viewModelScope.launch {
            saveDarkModeStateUseCase(this, appDarkMode.name)
            readDarkModeState()
        }
    }

    private fun readDarkModeState() {
        try {
            viewModelScope.launch {
                readDarkModeStateUseCase(this, Unit).apply {
                    onSuccess {
                        it.collect { darkMode ->
                            updateDarkMode(darkMode)
                        }
                    }
                    onFailure {
                        updateDarkMode(AppDarkMode.Default.name)
                    }
                }

            }
        } catch (e: Exception) {
            updateDarkMode(AppDarkMode.Default.name)
        }
    }


    private fun handleDarkModeMenu() {
        _uiState.update { uiState ->
            uiState.copy(listItems = getItemsMenu())
        }
    }

    private fun updateDarkMode(darkMode: String) {
        val appDarkMode = getAppDarkMode(darkMode)
        _uiState.update { uiState ->
            uiState.copy(
                appDarkMode = appDarkMode,
                appDarkModeSelected = getDarkModeSelected(appDarkMode)
            )
        }
    }

    private fun getAppDarkMode(darkMode: String) : AppDarkMode {
        return if (darkMode.isEmpty()) {
            AppDarkMode.Default
        } else {
            AppDarkMode.valueOf(darkMode)
        }
    }

    private fun getDarkModeSelected(darkMode: AppDarkMode) : DarkModeDropDownState {
        return when(darkMode) {
            AppDarkMode.Default -> DarkModeDropDownState()
            AppDarkMode.Dark -> getDarkModeState()
            AppDarkMode.Light -> getLightModeState()
        }
    }

    private fun getItemsMenu() = listOf(
        DarkModeDropDownState(),
        getDarkModeState(),
        getLightModeState()
    )

    private fun getDarkModeState() = DarkModeDropDownState(
            appDarkMode = AppDarkMode.Dark,
            titleRes = R.string.drawer_dark_mode_enabled,
            iconRes = R.drawable.ic_dark_mode_24
    )

    private fun getLightModeState() = DarkModeDropDownState(
        appDarkMode = AppDarkMode.Light,
        titleRes = R.string.drawer_dark_mode_disabled,
        iconRes = R.drawable.ic_light_mode_24
    )
}