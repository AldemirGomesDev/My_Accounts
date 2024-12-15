package br.com.aldemir.authentication.presentation

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.authentication.data.BiometricHelper
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.domain.usecase.authentication.InsertUserUseCase
import br.com.aldemir.domain.usecase.authentication.LoginUseCase
import br.com.aldemir.domain.usecase.authentication.Params
import br.com.aldemir.login.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val biometricHelper: BiometricHelper,
    private val insertUserUseCase: InsertUserUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    fun setToken(plainText: String) {
        Log.w("TAG_auth", "setToken: $plainText")
    }

    private val _uiState = MutableStateFlow(AuthenticationUiModel())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<AuthenticationEffect>(replay = 0)
    val uiEffect = _uiEffect.asSharedFlow()

    fun checkIfBiometricLoginEnabled() {
        _uiState.update {
            it.copy(isBiometricAvailable = biometricHelper.isBiometricAvailable())
        }
    }

    private fun registerUserBiometrics(fragmentActivity: FragmentActivity) {
        biometricHelper.registerUserBiometrics(fragmentActivity, onSuccess = {
            handleUiState(uiState.value.copy(state = AuthenticationState.SUCCESS))
            emitEffect(AuthenticationEffect.NavigateToHomeScreen)
        })
    }

    private fun authenticateUser(fragmentActivity: FragmentActivity) {
        biometricHelper.authenticateUser(
            fragmentActivity,
            onSuccess = {
                handleUiState(uiState.value.copy(state = AuthenticationState.SUCCESS))
                emitEffect(AuthenticationEffect.NavigateToHomeScreen)
            }
        )
    }

    fun checkPreferencesEnabled(fragmentActivity: FragmentActivity) {
        if (biometricHelper.checkPreferencesEnabled()) {
            authenticateUser(fragmentActivity)
        } else {
            registerUserBiometrics(fragmentActivity)
        }
    }

    fun loginUser(user: String, password: String) {
        viewModelScope.launch {
            handleUiLoading()
            delay(1000)
            if (user.isEmpty() || password.isEmpty()) {
                handleUiError(R.string.snack_bar_empty)
            } else {
                loginUseCase(this, Params(user, password)).apply {
                    onSuccess {
                        if (it != null) {
                            handleUiSuccess()
                        } else {
                            handleUiError(
                                R.string.snack_bar_user_or_password_error
                            )
                        }
                    }
                    onFailure {
                        handleUiError(
                            R.string.snack_bar_user_or_password_error
                        )
                    }
                }
            }
        }
    }

    private fun handleUiLoading() {
        handleUiState(
            uiState.value.copy(
                isLoading = true,
                isError = false,
                snackBarState = SnackBarState.NONE
            )
        )
    }

    private fun handleUiSuccess() {
        handleUiState(uiState.value.copy(
            isLoading = false,
            isError = false,
            state = AuthenticationState.SUCCESS,
            snackBarState = SnackBarState.NONE
        ))
        emitEffect(AuthenticationEffect.NavigateToHomeScreen)
    }

    private fun handleUiError(message: Int) {
        handleUiState(
            uiState.value.copy(
                isLoading = false,
                isError = true,
                snackBarState = SnackBarState.ERROR,
                snackBarMessage = message
            )
        )
    }

    private fun handleUiState(uiModel: AuthenticationUiModel) {
        _uiState.update { uiModel }
    }

    private fun emitEffect(effect: AuthenticationEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}