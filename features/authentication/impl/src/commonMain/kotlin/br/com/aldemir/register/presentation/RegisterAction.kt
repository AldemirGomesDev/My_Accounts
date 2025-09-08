package br.com.aldemir.register.presentation

sealed class RegisterAction {
    data class NameChange(val name: String) : RegisterAction()

    data class UserNameChange(val userName: String) : RegisterAction()

    data class PasswordChange(val password: String) : RegisterAction()

    data class ConfirmPasswordChange(val confirmPassword: String) : RegisterAction()

    data object RegisterClick : RegisterAction()

    data object BackClick : RegisterAction()
}