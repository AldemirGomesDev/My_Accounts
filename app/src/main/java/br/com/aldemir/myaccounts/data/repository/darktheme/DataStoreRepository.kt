package br.com.aldemir.myaccounts.data.repository.darktheme

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveDarkModeState(isDarkMode: Boolean)
    val readDarkModeState: Flow<Boolean>
}