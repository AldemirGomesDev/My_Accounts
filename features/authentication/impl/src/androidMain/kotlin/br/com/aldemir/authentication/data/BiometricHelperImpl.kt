package br.com.aldemir.authentication.data

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.UUID

private const val SECRET_KEY = "secret_key"
private const val ENCRYPTED_FILE_NAME = "encrypted_file_name"
private const val PREF_BIOMETRIC = "pref_biometric"

class BiometricHelperImpl(
    private val cryptoManager: CryptoManager,
    private val context: Context
) : BiometricHelper {
    override fun isBiometricAvailable(): Boolean {

        val biometricManager = BiometricManager.from(context)
        val canAuthenticationState = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.BIOMETRIC_WEAK)
        return when (canAuthenticationState) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                cryptoManager.initSecretKey()
                Log.e("TAG_auth", "Biometric authentication success")
                true
            }
            else -> {
                Log.e("TAG_auth", "Biometric authentication not available")
                false
            }
        }
    }

    override fun getBiometricPrompt(
        context: FragmentActivity,
        onAuthSucceed: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val biometricPrompt =
            BiometricPrompt(
                context,
                ContextCompat.getMainExecutor(context),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        Log.i("TAG_auth", "Authentication Succeeded: ${result.cryptoObject}")
                        onAuthSucceed(result)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        Log.e("TAG_auth", "onAuthenticationError")
                    }

                    override fun onAuthenticationFailed() {
                        Log.e("TAG_auth", "onAuthenticationFailed")
                    }
                }
            )
        return biometricPrompt
    }

    override fun getPromptInfo(dialogModel: DialogModel): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(dialogModel.title)
            .setSubtitle(dialogModel.subtitle)
            .setDescription(dialogModel.description)
            .setConfirmationRequired(false)
            .setNegativeButtonText(dialogModel.negativeButtonText)
            .build()
    }

    override fun registerUserBiometrics(
        context: FragmentActivity,
        dialogModel: DialogModel,
        onSuccess: (authResult: BiometricPrompt.AuthenticationResult) -> Unit
    ) {
        val cipher = cryptoManager.initEncryptionCipher(SECRET_KEY)
        val biometricPrompt =
            getBiometricPrompt(context) { authResult ->
                authResult.cryptoObject?.cipher?.let { cipher ->
                    val token = UUID.randomUUID().toString()
                    val encryptedToken = cryptoManager.encrypt(token, cipher)
                    cryptoManager.saveToPrefs(
                        encryptedToken,
                        context,
                        ENCRYPTED_FILE_NAME,
                        Context.MODE_PRIVATE,
                        PREF_BIOMETRIC
                    )
                    onSuccess(authResult)
                }
            }
        biometricPrompt.authenticate(getPromptInfo(dialogModel), BiometricPrompt.CryptoObject(cipher))
    }

    override fun authenticateUser(
        context: FragmentActivity,
        dialogModel: DialogModel,
        onSuccess: (plainText: String) -> Unit) {
        val encryptedData =
            cryptoManager.getFromPrefs(
                context,
                ENCRYPTED_FILE_NAME,
                Context.MODE_PRIVATE,
                PREF_BIOMETRIC
            )
        encryptedData?.let { data ->
            val cipher = cryptoManager.initDecryptionCipher(SECRET_KEY, data.initializationVector)
            val biometricPrompt =
                getBiometricPrompt(context) { authResult ->
                    authResult.cryptoObject?.cipher?.let { cipher ->
                        val plainText = cryptoManager.decrypt(data.ciphertext, cipher)
                        // Execute custom action on successful authentication
                        onSuccess(plainText)
                    }
                }
            val promptInfo = getPromptInfo(dialogModel)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

    override fun checkPreferencesEnabled(): Boolean {
        val encryptedData =
            cryptoManager.getFromPrefs(
                context,
                ENCRYPTED_FILE_NAME,
                Context.MODE_PRIVATE,
                PREF_BIOMETRIC
            )
        return encryptedData != null
    }
}