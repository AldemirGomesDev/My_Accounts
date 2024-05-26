package br.com.aldemir.authentication.presentation

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.authentication.data.BiometricHelper
import br.com.aldemir.authentication.data.CryptoManagerImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val biometricHelper: BiometricHelper
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

    private fun handleUiState(uiModel: AuthenticationUiModel) {
        _uiState.update { uiModel }
    }

    private fun emitEffect(effect: AuthenticationEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}