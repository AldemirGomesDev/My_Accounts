package br.com.aldemir.authentication.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import br.com.aldemir.authentication.data.BiometricHelperImpl
import br.com.aldemir.authentication.data.CryptoManagerImpl
import org.koin.androidx.compose.koinViewModel

private const val ENCRYPTED_FILE_NAME = "encrypted_file_name"
private const val PREF_BIOMETRIC = "pref_biometric"

@Composable
fun LoginScreen(
    navigateToHomeScreen: () -> Unit,
) {
    val context = LocalContext.current as FragmentActivity
    val viewModel: LoginViewModel = koinViewModel()

    val uiModel by viewModel.uiState.collectAsState()

    val uiEffect by viewModel.uiEffect.collectAsState(Unit)

    LaunchedEffect(key1 = uiEffect) {
        if (uiEffect is AuthenticationEffect.NavigateToHomeScreen) {
            navigateToHomeScreen()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.checkIfBiometricLoginEnabled()
    }

    LaunchedEffect(key1 = uiModel) {
        if (uiModel.state == AuthenticationState.SUCCESS) {
            Log.w("TAG_auth", "SUCCESS")
        }
    }

    LaunchedEffect(key1 = uiModel.isBiometricAvailable) {
        if (uiModel.isBiometricAvailable) {
            viewModel.checkPreferencesEnabled(context)
        }
        Log.d("TAG_auth", "isBiometricAvailable $uiModel.isBiometricAvailable")
    }

}