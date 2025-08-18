package br.com.aldemir.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.aldemir.common.component.CustomSnackBar
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.theme.MyAccountsFont
import br.com.aldemir.common.theme.MyAccountsTheme
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = false,
    navigateToLoginScreen: () -> Unit
) {
    val viewModel: RegisterViewModel = koinViewModel()

    val snackbarHostState = remember { SnackbarHostState() }

    val uiModel by viewModel.uiState.collectAsStateWithLifecycle()

    val messageError = stringResource(uiModel.snackBarUiModel.message)

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is RegisterEffect.NavigateToLogin -> {
                    snackbarHostState.showSnackbar(
                        message = messageError,
                        duration = SnackbarDuration.Short
                    )
                    navigateToLoginScreen.invoke()
                }
                is RegisterEffect.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = messageError,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> Unit
                }
            }
    }


    RegisterContent(
        modifier = modifier,
        uiModel = uiModel,
        onAction = { action ->
            viewModel.onAction(action)
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun RegisterContent(
    modifier: Modifier = Modifier,
    uiModel: RegisterUiModel,
    onAction: (RegisterAction) -> Unit = {},
    snackbarHostState: SnackbarHostState
) {

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                CustomSnackBar(
                    snackBarState = uiModel.snackBarUiModel.snackBarState,
                    message = data.visuals.message,
                )
            }
        },
        content = { it ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MyAccountsTheme.colors.background)
                    .padding(it)
                    .padding(
                        start = MyAccountsTheme.dimensions.padding20,
                        top = MyAccountsTheme.dimensions.padding64,
                        end = MyAccountsTheme.dimensions.padding20
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cadastro",
                    color = MyAccountsTheme.colors.primary,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = MyAccountsFont
                    )
                )

                Spacer(modifier = Modifier.height(MyAccountsTheme.dimensions.sizing24))

                InputTextOutlinedTextField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = uiModel.name,
                    onValueChange = { name ->
                        onAction(RegisterAction.NameChange(name))
                    },
                    label = "Nome:",
                    isError = false,
                    shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                )
                Spacer(modifier = Modifier.height(20.dp))
                InputTextOutlinedTextField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = uiModel.userName,
                    onValueChange = { useName ->
                        onAction(RegisterAction.UserNameChange(useName))
                    },
                    label = "Login:",
                    isError = false,
                    shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                )
                Spacer(modifier = Modifier.height(20.dp))
                InputTextOutlinedTextField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = uiModel.password,
                    onValueChange = { password ->
                        onAction(RegisterAction.PasswordChange(password))
                    },
                    label = "Senha:",
                    isError = false,
                    shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(20.dp))
                InputTextOutlinedTextField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = uiModel.confirmPassword,
                    onValueChange = { confirmPassword ->
                        onAction(RegisterAction.ConfirmPasswordChange(confirmPassword))
                    },
                    label = "Repetir Senha:",
                    isError = false,
                    shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)) {
                    LoadingButton(
                        onClick = {
                            onAction(RegisterAction.RegisterClick)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MyAccountsTheme.dimensions.sizing52),
                        loading = uiModel.isLoading,
                        enabled = true,
                        shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                        text = "Cadastrar",
                    )
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun RegisterScreenPreview() {
    MyAccountsTheme {
        RegisterContent(
            uiModel = RegisterUiModel(),
            onAction = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}