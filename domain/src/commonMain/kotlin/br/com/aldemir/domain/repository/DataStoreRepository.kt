package br.com.aldemir.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveDarkModeState(isDarkMode: String)
    val readDarkModeState: Flow<String>

    suspend fun saveStartTime(startTimeMillis: Long)
    suspend fun getStartTime(): Long?
    suspend fun clearStartTime()
    suspend fun setCountTimeFinished(isFinished: Boolean)
    suspend fun isCountTimeFinished(): Boolean
}