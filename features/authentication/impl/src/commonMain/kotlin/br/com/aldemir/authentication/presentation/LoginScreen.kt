package br.com.aldemir.authentication.presentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
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
import br.com.aldemir.common.R
import br.com.aldemir.common.component.CustomSnackBar
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.component.LoadingAnimation
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.common.theme.MyAccountsFont
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.util.emptyString


@Composable
fun LoginScreen(
    isDarkTheme: Boolean,
    navigateToHomeScreen: () -> Unit,
) {
    val context = LocalContext.current as FragmentActivity
    val viewModel: LoginViewModel = koinViewModel()

    val uiModel by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkIfBiometricLoginEnabled()
    }

    val activity = (LocalContext.current as? Activity)

    BackHandler { activity?.finish() }

    when(uiModel.state) {
        AuthenticationState.SUCCESS -> {
            LoadingScreen()
            navigateToHomeScreen()
        }
        AuthenticationState.IDLE -> {
            LoginPage(
                isDarkTheme = isDarkTheme,
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
    }
}

@Composable
fun LoginPage(
    isDarkTheme: Boolean,
    uiModel: AuthenticationUiModel,
    loginOnclick: (userName: String, password: String) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val messageError = stringResource(id = uiModel.snackBarMessage)

    LaunchedEffect(uiModel.snackBarState) {
        when(uiModel.snackBarState) {
            SnackBarState.ERROR -> {
                snackbarHostState.showSnackbar(
                    message = messageError,
                    duration = SnackbarDuration.Short
                )
            }
            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                CustomSnackBar(
                    snackBarState = uiModel.snackBarState,
                    message = data.visuals.message,
                )
            }
        },
        containerColor = MyAccountsTheme.colors.background,
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

                Image(
                    modifier = Modifier
                        .size(MyAccountsTheme.dimensions.sizing120),
                    painter = painterResource(id = getLogo(isDarkTheme)),
                    contentDescription = stringResource(id = R.string.account_logo)
                )

                Text(
                    modifier = Modifier.padding(top = MyAccountsTheme.dimensions.padding24),
                    text = "Minhas contas",
                    color = MyAccountsTheme.colors.primary,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = MyAccountsFont
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
                        text = "Entrar",
                    )
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
                Spacer(modifier = Modifier.weight(1f))
                ClickableText(
                    text = AnnotatedString("Cadastre-se aqui"),
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = { },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = MyAccountsTheme.colors.primary
                    )
                )
            }
        }
    )
}

@Composable
private fun getLogo(isDarkTheme: Boolean): Int {
    return if (isDarkTheme) {
        R.drawable.icon_despesa_light
    } else {
        R.drawable.icon_despesa
    }
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
            isDarkTheme = true,
            uiModel = AuthenticationUiModel(),
            loginOnclick = { _, _ -> }
        )
    }
}