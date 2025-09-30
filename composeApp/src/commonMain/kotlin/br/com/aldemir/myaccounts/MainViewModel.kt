package br.com.aldemir.myaccounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.model.UserLogged
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import br.com.aldemir.common.theme.AppDarkMode
import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.usecase.authentication.GetLoggerUserState
import br.com.aldemir.domain.usecase.authentication.GetLoggerUserUseCase
import br.com.aldemir.domain.usecase.authentication.LogoutUseCase
import br.com.aldemir.domain.usecase.darkmode.ReadDarkModeStateUseCase
import br.com.aldemir.domain.usecase.darkmode.SaveDarkModeStateUseCase
import com.diamondedge.logging.logging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val saveDarkModeStateUseCase: SaveDarkModeStateUseCase,
    private val readDarkModeStateUseCase: ReadDarkModeStateUseCase,
    private val getLoggerUserUseCase: GetLoggerUserUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val log = logging("TAG_auth")
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        getLoggerUserUseCase(viewModelScope, UseCase.None()) {
            success = { stateFlow ->
                updateLoggerUser(stateFlow)
            }
        }
    }

    private fun updateLoggerUser(stateFlow: Flow<GetLoggerUserState>) {
        viewModelScope.launch {
            stateFlow.collect { state ->
                log.info { "MainViewModel -> updateLoggerUser: $state" }
                if (state is GetLoggerUserState.LoggedUser) {
                    _uiState.update { uiState ->
                        uiState.copy(userLogged = state.userDomain.toUserLogger())
                    }
                }
            }
        }
    }

    fun onAction(action: MainAction) {
        when (action) {
            MainAction.FetchData -> {
                handleDarkModeMenu()
                readDarkModeState()
            }
            is MainAction.UpdateDarkModeState -> saveDarkModeState(action.appDarkMode)
            is MainAction.Logout -> {
                logoutUseCase(viewModelScope, action.userName) {
                    success = {
                        _uiState.update { uiState ->
                            uiState.copy(userLogged = UserLogged())
                        }
                    }
                }
            }
        }
    }

    private fun saveDarkModeState(appDarkMode: AppDarkMode) {
        viewModelScope.launch {
            saveDarkModeStateUseCase(this, appDarkMode.name) {}
            readDarkModeState()
        }
    }

    private fun readDarkModeState() {
        try {
            readDarkModeStateUseCase(viewModelScope, Unit) {
                success = { flow ->
                    viewModelScope.launch {
                        flow.collect { darkMode ->
                            updateDarkMode(darkMode)
                        }
                    }
                }
                error = {
                    updateDarkMode(AppDarkMode.Default.name)
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


    private fun getItemsMenu() = listOf(
        AppDarkMode.Default,
        AppDarkMode.Dark,
        AppDarkMode.Light
    )
}