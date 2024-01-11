package br.com.aldemir.data.repository.darktheme

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveDarkModeState(isDarkMode: Boolean)
    val readDarkModeState: Flow<Boolean>
}