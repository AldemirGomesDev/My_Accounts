package br.com.aldemir.authentication.presentation

sealed class AuthenticationEffect {
    data object NavigateToHomeScreen: AuthenticationEffect()
}