package br.com.aldemir.authentication.presentation

data class AuthenticationUiModel(
    val state: AuthenticationState = AuthenticationState.IDLE,
    val isBiometricAvailable: Boolean = false,
    val showBiometricPrompt: Boolean = false
)
