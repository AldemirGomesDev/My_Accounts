package br.com.aldemir.authentication.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import br.com.aldemir.common.theme.MyAccountsTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.aldemir.common.component.CustomSnackBar
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.component.LoadingAnimation
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.common.theme.FontSize
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.theme.Purple700
import br.com.aldemir.common.util.emptyString


@Composable
fun LoginScreen(
    navigateToHomeScreen: () -> Unit,
) {
    val context = LocalContext.current as FragmentActivity
    val viewModel: LoginViewModel = koinViewModel()

    val uiModel by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkIfBiometricLoginEnabled()
    }

    when(uiModel.state) {
        AuthenticationState.SUCCESS -> {
            Log.w("TAG_auth", "SUCCESS")
            LoadingScreen()
            navigateToHomeScreen()
        }
        AuthenticationState.IDLE -> {
            Log.w("TAG_auth", "IDLE")
            LoginPage(
                uiModel = uiModel,
                loginOnclick = { userName, password ->
                    viewModel.loginUser(userName, password)
                }
            )
        }
    }

    LaunchedEffect(key1 = uiModel.isBiometricAvailable) {
        if (uiModel.isBiometricAvailable) {
            viewModel.checkPreferencesEnabled(context)
        }
        Log.d("TAG_auth", "isBiometricAvailable $uiModel.isBiometricAvailable")
    }
}

@Composable
fun LoginPage(
    uiModel: AuthenticationUiModel,
    loginOnclick: (userName: String, password: String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    val messageError = stringResource(id = uiModel.snackBarMessage)

    LaunchedEffect(uiModel.snackBarState) {
        when(uiModel.snackBarState) {
            SnackBarState.ERROR -> {
                Log.w("TAG_auth", "ERROR")
                scaffoldState.snackbarHostState.showSnackbar(
                    message = messageError,
                    duration = SnackbarDuration.Short
                )
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyAccountsTheme.colors.background),
    ) {
        ClickableText(
            text = AnnotatedString("Cadastre-se aqui"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = { },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Purple700
            )
        )
    }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                CustomSnackBar(
                    snackBarState = uiModel.snackBarState,
                    message = data.message,
                )
            }
        },
        backgroundColor = MyAccountsTheme.colors.background,
        content = {
            Column(
                modifier = Modifier.
                    padding(it)
                    .padding(
                        start = MyAccountsTheme.dimensions.padding20,
                        top = MyAccountsTheme.dimensions.padding64,
                        end = MyAccountsTheme.dimensions.padding20
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val username = remember { mutableStateOf(emptyString()) }
                val password = remember { mutableStateOf(emptyString()) }

                Text(
                    text = "Login",
                    color = MyAccountsTheme.colors.primary,
                    style = TextStyle(
                        fontSize = 40.sp,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.height(MyAccountsTheme.dimensions.sizing32))

                InputTextOutlinedTextField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = username.value,
                    onValueChange = {
                        username.value = it
                    },
                    label = "Usuário",
                    isError = false,
                    shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                )
                Spacer(modifier = Modifier.height(20.dp))
                InputTextOutlinedTextField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },
                    label = "Senha",
                    isError = false,
                    shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp)) {
                    LoadingButton(
                        onClick = {
                            loginOnclick(username.value, password.value)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MyAccountsTheme.dimensions.sizing52),
                        loading = uiModel.isLoading,
                        enabled = true,
                        shape = RoundedCornerShape(MyAccountsTheme.dimensions.sizing48),
                    ) {
                        Text(
                            color = Color.White,
                            text = "Entrar",
                            fontSize = FontSize.scale16,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                ClickableText(
                    text = AnnotatedString("Forgot password?"),
                    onClick = { },
                    style = TextStyle(
                        color = MyAccountsTheme.colors.primary,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default
                    )
                )
            }
        }
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MyAccountsTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        LoadingAnimation(
            circleColor = MyAccountsTheme.colors.primary
        )
    }
}

@PreviewLightDark
@Composable
private fun LoginPagePreview() {
    MyAccountsTheme {
        LoginPage(
            uiModel = AuthenticationUiModel(),
            loginOnclick = { _, _ -> }
        )
    }
}