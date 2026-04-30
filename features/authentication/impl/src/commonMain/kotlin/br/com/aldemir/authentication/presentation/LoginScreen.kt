package br.com.aldemir.authentication.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.ui.backhandler.BackHandler
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
//import androidx.compose.ui.platform.LocalContext
//import androidx.fragment.app.FragmentActivity
import br.com.aldemir.common.theme.MyAccountsTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.aldemir.authentication.data.DialogModel
import br.com.aldemir.authentication.presentation.effect.LoginEffect
import org.jetbrains.compose.resources.stringResource as stringRes
import br.com.aldemir.common.component.CustomSnackBar
import br.com.aldemir.common.component.InputTextOutlinedTextField
import br.com.aldemir.common.component.LoadingAnimation
import br.com.aldemir.common.component.LoadingButton
import br.com.aldemir.common.component.SnackBarState
import br.com.aldemir.common.model.UserLogged
import br.com.aldemir.common.theme.MyAccountsFont
import br.com.aldemir.common.theme.MyAccountsTheme.MyAccountsTheme
import br.com.aldemir.common.util.emptyString
import com.diamondedge.logging.logging
import myaccounts.common.generated.resources.icon_despesa
import myaccounts.common.generated.resources.icon_despesa_light
import myaccounts.features.authentication.impl.generated.resources.Res
import myaccounts.common.generated.resources.Res as ResCommon
import myaccounts.features.authentication.impl.generated.resources.biometric_prompt_description_text
import myaccounts.features.authentication.impl.generated.resources.biometric_prompt_subtitle_text
import myaccounts.features.authentication.impl.generated.resources.biometric_prompt_title_text
import myaccounts.features.authentication.impl.generated.resources.biometric_prompt_use_password_instead_text
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    isDarkTheme: Boolean,
    navigateToHomeScreen: (UserLogged) -> Unit,
    navigateToRegisterScreen: () -> Unit,
    onFinish: () -> Unit,
) {
//    val context = LocalContext.current as FragmentActivity
    val viewModel: LoginViewModel = koinViewModel()

    val uiModel by viewModel.uiState.collectAsState()

    val remainingTime = viewModel.remainingTime
    val finished = viewModel.isFinished

    val progress by animateFloatAsState(
        targetValue = viewModel.progress,
        animationSpec = tween(100),
        label = "progressAnimation"
    )

//    LaunchedEffect(Unit) {
//        viewModel.checkIfBiometricLoginEnabled()
//    }

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    val messageError = stringResource(uiModel.snackBarMessage)


    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is LoginEffect.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = messageError,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    BackHandler { onFinish.invoke() }

    when (uiModel.state) {
        AuthenticationState.SUCCESS -> {
            LoadingScreen()
            navigateToHomeScreen(uiModel.userLogged)
        }

        AuthenticationState.IDLE -> {
            LoginPage(
                isDarkTheme = isDarkTheme,
                uiModel = uiModel,
                loginOnclick = { userName, password ->
                    viewModel.loginUser(userName, password)
                },
                navigateToRegisterScreen = {
                    navigateToRegisterScreen()
                },
                snackbarHostState = snackbarHostState,
                finished = finished,
                remainingTime = remainingTime,
                progress = progress,
                onResetTimeClicked = { viewModel.reset() }
            )
        }
    }
    val dialogModel = getDialogModel()

//    LaunchedEffect(key1 = uiModel.isBiometricAvailable) {
//        if (uiModel.isBiometricAvailable) {
//            viewModel.checkPreferencesEnabled(context, dialogModel)
//        }
//    }
}

@Composable
fun LoginPage(
    isDarkTheme: Boolean,
    uiModel: AuthenticationUiModel,
    loginOnclick: (userName: String, password: String) -> Unit,
    navigateToRegisterScreen: () -> Unit,
    snackbarHostState: SnackbarHostState,
    finished: Boolean,
    remainingTime: kotlin.time.Duration,
    progress: Float = 0f,
    onResetTimeClicked: () -> Unit = {}
) {

//    val messageError = stringResource(uiModel.snackBarMessage)
//
//    LaunchedEffect(uiModel.snackBarState) {
//
//        when (uiModel.snackBarState) {
//            SnackBarState.ERROR -> {
//                snackbarHostState.showSnackbar(
//                    message = messageError,
//                    duration = SnackbarDuration.Short
//                )
//            }
//
//            else -> Unit
//        }
//    }



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
                modifier = Modifier
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

                val username = remember { mutableStateOf(emptyString()) }
                val password = remember { mutableStateOf(emptyString()) }

                Image(
                    modifier = Modifier
                        .size(MyAccountsTheme.dimensions.sizing120),
                    painter = painterResource(getLogo(isDarkTheme)),
                    contentDescription = null
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
                CircularCountdown(progress = progress, remainingTime = remainingTime, finished = finished)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { onResetTimeClicked.invoke() },
                    ) {
                        Text("Reiniciar")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                ClickableText(
                    text = AnnotatedString("Cadastre-se aqui"),
                    modifier = Modifier
                        .padding(20.dp),
                    onClick = { navigateToRegisterScreen() },
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
private fun getLogo(isDarkTheme: Boolean): DrawableResource {
    return if (isDarkTheme) {
        ResCommon.drawable.icon_despesa_light
    } else {
        ResCommon.drawable.icon_despesa
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


@Composable
private fun CircularCountdown(
    progress: Float,
    remainingTime: kotlin.time.Duration,
    finished: Boolean
) {
    val stroke = 12.dp

    val green = Color(0xFF4CAF50)
    val orange = Color(0xFFFFA000)
    val red = Color(0xFFF44336)

    val targetColor = when {
        progress > 0.5f -> {
            val t = (progress - 0.5f) / 0.5f
            lerp(orange, green, t)
        }
        else -> {
            val t = progress / 0.5f
            lerp(red, orange, t)
        }
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 800),
        label = "progressColor"
    )

    if (!finished) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .padding(12.dp)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawArc(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = animatedColor,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round)
                )
            }
            Text(
                text = "${remainingTime.inWholeSeconds}s",
                style = MyAccountsTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        Text(
            text = "Tempo esgotado",
            style = MyAccountsTheme.typography.h3,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
private fun getDialogModel(): DialogModel {
    return DialogModel(
        title = stringRes(Res.string.biometric_prompt_title_text),
        subtitle = stringRes(Res.string.biometric_prompt_subtitle_text),
        description = stringRes(Res.string.biometric_prompt_description_text),
        negativeButtonText = stringRes(Res.string.biometric_prompt_use_password_instead_text)
    )
}

@Composable
private fun LoginPagePreview() {
    MyAccountsTheme {
        LoginPage(
            isDarkTheme = true,
            uiModel = AuthenticationUiModel(
                state = AuthenticationState.SUCCESS,
            ),
            loginOnclick = { _, _ -> },
            navigateToRegisterScreen = {},
            snackbarHostState = SnackbarHostState(),
            finished = false,
            remainingTime = kotlin.time.Duration.ZERO
        )
    }
}