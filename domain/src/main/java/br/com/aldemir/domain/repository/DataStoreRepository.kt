package br.com.aldemir.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveDarkModeState(isDarkMode: String)
    val readDarkModeState: Flow<String>
}