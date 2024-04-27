package br.com.aldemir.data.database.preference

import kotlinx.coroutines.flow.Flow

interface DataStorePreference {
    suspend fun saveDarkModeState(isDarkMode: String)
    val readDarkModeState: Flow<String>
}