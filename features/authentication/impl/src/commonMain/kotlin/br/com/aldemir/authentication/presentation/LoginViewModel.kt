package br.com.aldemir.authentication.presentation

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aldemir.authentication.data.BiometricHelper
import br.com.aldemir.authentication.data.DialogModel
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.domain.usecase.authentication.LoginUseCase
import br.com.aldemir.domain.usecase.authentication.LoginUseCaseState
import br.com.aldemir.domain.usecase.authentication.Params
import br.com.aldemir.domain.usecase.post.GetAllPostsUseCase
import br.com.aldemir.domain.usecase.product.GetAllProductsUseCase
import br.com.aldemir.login.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val biometricHelper: BiometricHelper,
    private val loginUseCase: LoginUseCase,
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiModel())
    val uiState = _uiState.asStateFlow()

    private fun getAllPosts() {
        viewModelScope.launch {
            getAllPostsUseCase(viewModelScope, Unit).apply {
                onSuccess {
                    // Handle success if needed
                    Log.d("TAG_auth", "sucesso: ${it.first()}")
                }
                onFailure {
                    Log.e("TAG_auth", "Error: $it")
                    // Handle failure if needed
                }
            }
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase(viewModelScope, Unit).apply {
                onSuccess {
                    // Handle success if needed
                    Log.d("TAG_auth", "sucesso-> Product ${it.toList().random()}")
                }
                onFailure {
                    Log.e("TAG_auth", "Error: $it")
                    // Handle failure if needed
                }
            }
        }
    }

    fun checkIfBiometricLoginEnabled() {
        _uiState.update {
            it.copy(isBiometricAvailable = biometricHelper.isBiometricAvailable())
        }
    }

    private fun registerUserBiometrics(
        fragmentActivity: FragmentActivity,
        dialogModel: DialogModel
    ) {
        biometricHelper.registerUserBiometrics(
            fragmentActivity,
            dialogModel,
            onSuccess = {
                handleUiState(uiState.value.copy(state = AuthenticationState.SUCCESS))
            }
        )
    }

    private fun authenticateUser(
        fragmentActivity: FragmentActivity,
        dialogModel: DialogModel
    ) {
        biometricHelper.authenticateUser(
            fragmentActivity,
            dialogModel,
            onSuccess = {
                handleUiState(uiState.value.copy(state = AuthenticationState.SUCCESS))
            }
        )
    }

    fun checkPreferencesEnabled(fragmentActivity: FragmentActivity, dialogModel: DialogModel) {
        if (biometricHelper.checkPreferencesEnabled()) {
            authenticateUser(fragmentActivity, dialogModel)
        } else {
            registerUserBiometrics(fragmentActivity, dialogModel)
        }
    }

    fun loginUser(userName: String, password: String) {
        getAllProducts()
        viewModelScope.launch {
            handleUiLoading()
            if (checkUserNameAndPasswordIsEmpty(userName, password)) {
                delay(500)
                handleUiError(R.string.snack_bar_empty)
            } else {
                delay(1000)
                loginUseCase(this, Params(userName, password)).apply {
                    onSuccess {
                        handleLoginSuccess(it)
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

    private fun handleLoginSuccess(loginUseCaseState: LoginUseCaseState) {
        when (loginUseCaseState) {
            is LoginUseCaseState.Success -> {
                handleUiSuccess()
            }

            is LoginUseCaseState.NotFound -> {
                handleUiError(R.string.snack_bar_user_or_password_error)
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
        handleUiState(
            uiState.value.copy(
                isLoading = false,
                isError = false,
                state = AuthenticationState.SUCCESS,
                snackBarState = SnackBarState.NONE
            )
        )
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

    private fun checkUserNameAndPasswordIsEmpty(userName: String, password: String): Boolean {
        return (userName.isEmpty() || password.isEmpty())
    }
}