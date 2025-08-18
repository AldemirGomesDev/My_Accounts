package br.com.aldemir.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.domain.model.UserDomain
import br.com.aldemir.domain.usecase.authentication.InsertUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.features.authentication.impl.generated.resources.Res
import myaccounts.features.authentication.impl.generated.resources.register_empty_fields
import myaccounts.features.authentication.impl.generated.resources.register_error
import myaccounts.features.authentication.impl.generated.resources.register_success

class RegisterViewModel(
    private val insertUserUseCase: InsertUserUseCase,
    private val mapper: RegisterUiMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiModel())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<RegisterEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.NameChange -> onNameChange(action.name)
            is RegisterAction.UserNameChange -> onUserNameChange(action.userName)
            is RegisterAction.PasswordChange -> onPasswordChange(action.password)
            is RegisterAction.ConfirmPasswordChange -> onConfirmPasswordChange(action.confirmPassword)
            RegisterAction.RegisterClick -> onRegisterClick()
            RegisterAction.BackClick -> onBackClick()
        }
    }

    private fun onNameChange(name: String) {
        val current = _uiState.value
        _uiState.update { current.copy(name = name) }
    }

    private fun onUserNameChange(userName: String) {
        val current = _uiState.value
        _uiState.update { current.copy(userName = userName) }
    }

    private fun onPasswordChange(password: String) {
        val current = _uiState.value
        _uiState.update { current.copy(password = password) }
    }

    private fun onConfirmPasswordChange(confirmPassword: String) {
        val current = _uiState.value
        _uiState.update { current.copy(confirmPassword = confirmPassword) }
    }

    private fun onRegisterClick() {
        if (checkAllFieldIsEmpty()) {
            triggerEffect(RegisterEffect.ShowSnackBar(Res.string.register_empty_fields))
            _uiState.update {
                mapper.mapToUiModel(Res.string.register_empty_fields, SnackBarState.ERROR, it)
            }
            return
        }
        handleUiLoading()
        val user = UserDomain(
            name = uiState.value.name,
            userName = uiState.value.userName,
            password = uiState.value.password,
            isLogged = false
        )
        viewModelScope.launch {
            insertUserUseCase(viewModelScope, user).apply {
                onSuccess {
                    triggerEffect(RegisterEffect.NavigateToLogin)
                    _uiState.update {
                        mapper.mapToUiModel(Res.string.register_success, SnackBarState.SUCCESS)
                    }
                }
                onFailure {
                    triggerEffect(RegisterEffect.ShowSnackBar(Res.string.register_error))
                    _uiState.update {
                        mapper.mapToUiModel(Res.string.register_error, SnackBarState.ERROR, it)
                    }
                }
            }
        }
    }

    private fun triggerEffect(effect: RegisterEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun handleUiLoading() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                )
            }
        }
    }

    private fun onBackClick() {

    }

    private fun checkAllFieldIsEmpty(): Boolean {
        return (_uiState.value.name.isEmpty() ||
                _uiState.value.userName.isEmpty() ||
                _uiState.value.password.isEmpty() ||
                _uiState.value.confirmPassword.isEmpty())
    }
}