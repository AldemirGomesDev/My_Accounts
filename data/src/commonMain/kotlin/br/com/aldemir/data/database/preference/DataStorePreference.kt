package br.com.aldemir.data.database.preference

import kotlinx.coroutines.flow.Flow

interface DataStorePreference {
    suspend fun saveDarkModeState(isDarkMode: String)
    val readDarkModeState: Flow<String>

    suspend fun saveStartTime(startTimeMillis: Long)
    suspend fun getStartTime(): Long?
    suspend fun clearStartTime()
    suspend fun setCountTimeFinished(isFinished: Boolean)
    suspend fun isCountTimeFinished(): Boolean
}