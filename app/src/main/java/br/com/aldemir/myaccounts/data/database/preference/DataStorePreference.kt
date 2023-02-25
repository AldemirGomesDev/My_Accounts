package br.com.aldemir.myaccounts.data.database.preference

import kotlinx.coroutines.flow.Flow

interface DataStorePreference {
    suspend fun saveDarkModeState(isDarkMode: Boolean)
    val readDarkModeState: Flow<Boolean>
}