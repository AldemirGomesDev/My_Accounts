package br.com.aldemir.authentication.data

import android.content.Context
import javax.crypto.Cipher

interface CryptoManager {
    fun initEncryptionCipher(keyName: String): Cipher

    fun initDecryptionCipher(keyName: String, initializationVector: ByteArray): Cipher

    fun encrypt(plaintext: String, cipher: Cipher): EncryptedData

    fun decrypt(ciphertext: ByteArray, cipher: Cipher): String

    fun saveToPrefs(
        encryptedData: EncryptedData,
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    )

    fun getFromPrefs(
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ): EncryptedData?
}