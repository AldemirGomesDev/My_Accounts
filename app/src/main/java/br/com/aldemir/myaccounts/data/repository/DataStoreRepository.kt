package br.com.aldemir.myaccounts.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveDarkModeState(isDarkMode: Boolean)
    val readDarkModeState: Flow<Boolean>
}