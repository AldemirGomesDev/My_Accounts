package br.com.aldemir.authentication.data

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson

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