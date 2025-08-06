package br.com.aldemir.authentication.data

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

interface BiometricHelper {
    fun isBiometricAvailable(): Boolean
    fun getBiometricPrompt(
        context: FragmentActivity,
        onAuthSucceed: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt
    fun getPromptInfo(context: FragmentActivity): BiometricPrompt.PromptInfo
    fun registerUserBiometrics(
        context: FragmentActivity,
        onSuccess: (authResult: BiometricPrompt.AuthenticationResult) -> Unit = {}
    )
    fun authenticateUser(context: FragmentActivity, onSuccess: (plainText: String) -> Unit)
    fun checkPreferencesEnabled(): Boolean
}