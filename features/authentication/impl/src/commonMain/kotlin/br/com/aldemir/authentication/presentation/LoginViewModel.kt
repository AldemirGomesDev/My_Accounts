package br.com.aldemir.authentication.presentation

//import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import br.com.aldemir.authentication.data.BiometricHelper
import br.com.aldemir.authentication.data.DialogModel
import br.com.aldemir.authentication.presentation.effect.LoginEffect
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.domain.base.UseCase
import br.com.aldemir.domain.usecase.authentication.GetLoggerUserState
import br.com.aldemir.domain.usecase.authentication.GetLoggerUserUseCase
import br.com.aldemir.domain.usecase.authentication.LoginUseCase
import br.com.aldemir.domain.usecase.authentication.LoginUseCaseState
import br.com.aldemir.domain.usecase.authentication.Params
import br.com.aldemir.domain.usecase.post.GetAllPostsUseCase
import br.com.aldemir.domain.usecase.product.GetAllProductsUseCase
import com.diamondedge.logging.logging
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import myaccounts.common.generated.resources.Res
import myaccounts.common.generated.resources.snack_bar_empty
import myaccounts.common.generated.resources.snack_bar_user_or_password_error
import org.jetbrains.compose.resources.StringResource

class LoginViewModel(
//    private val biometricHelper: BiometricHelper,
    private val loginUseCase: LoginUseCase,
    private val getLoggerUserUseCase: GetLoggerUserUseCase,
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiModel())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<LoginEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    private val log = logging("TAG_auth")

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
                log.info { "LoginViewModel -> updateLoggerUser: $state" }
                if (state is GetLoggerUserState.LoggedUser) {
                    handleUiSuccess()
                }
            }
        }
    }

    private fun getAllPosts() {
        viewModelScope.launch {
            getAllPostsUseCase(viewModelScope, Unit) {
                success = {
                    log.info { "sucesso: ${it.first()}" }
                }
                error = {
                    log.error { "Error: $it" }
                }
            }
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsUseCase(viewModelScope) {
                success = { listProductDomainModel ->
                    log.info { "getAllProducts -> sucesso: ${listProductDomainModel.toList().random()}" }
                }
                error = { error ->
                    log.error { "getAllProducts -> Error: $error" }
                }
            }
        }
    }

//    fun checkIfBiometricLoginEnabled() {
//        _uiState.update {
//            it.copy(isBiometricAvailable = biometricHelper.isBiometricAvailable())
//        }
//    }

//    private fun registerUserBiometrics(
//        fragmentActivity: FragmentActivity,
//        dialogModel: DialogModel
//    ) {
//        biometricHelper.registerUserBiometrics(
//            fragmentActivity,
//            dialogModel,
//            onSuccess = {
//                handleUiState(uiState.value.copy(state = AuthenticationState.SUCCESS))
//            }
//        )
//    }
//
//    private fun authenticateUser(
//        fragmentActivity: FragmentActivity,
//        dialogModel: DialogModel
//    ) {
//        biometricHelper.authenticateUser(
//            fragmentActivity,
//            dialogModel,
//            onSuccess = {
//                handleUiState(uiState.value.copy(state = AuthenticationState.SUCCESS))
//            }
//        )
//    }

//    fun checkPreferencesEnabled(fragmentActivity: FragmentActivity, dialogModel: DialogModel) {
//        if (biometricHelper.checkPreferencesEnabled()) {
//            authenticateUser(fragmentActivity, dialogModel)
//        } else {
//            registerUserBiometrics(fragmentActivity, dialogModel)
//        }
//    }

    fun loginUser(userName: String, password: String) {
        getAllProducts()
        viewModelScope.launch {
            handleUiLoading()
            if (checkUserNameAndPasswordIsEmpty(userName, password)) {
                delay(500)
                handleUiError(Res.string.snack_bar_empty)
            } else {
                delay(1000)
                loginUseCase(this, Params(userName, password))  {
                    success = {
                        handleLoginSuccess(it)
                        log.info { "Login -> success: $it" }
                    }
                    error = {
                        log.error { "Login -> error: ${it.message}" }
                        handleUiError(
                            Res.string.snack_bar_user_or_password_error
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
                handleUiError(Res.string.snack_bar_user_or_password_error)
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

    private fun handleUiError(message: StringResource) {
        viewModelScope.launch {
            _uiEffect.send(LoginEffect.ShowSnackBar)
        }
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