package br.com.aldemir.authentication.presentation.effect

sealed class LoginEffect {
    object ShowSnackBar : LoginEffect()
}