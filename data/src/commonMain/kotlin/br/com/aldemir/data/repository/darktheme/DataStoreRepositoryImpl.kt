package br.com.aldemir.data.repository.darktheme

import br.com.aldemir.data.database.preference.DataStorePreference
import br.com.aldemir.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class DataStoreRepositoryImpl(
    private val dataStorePreference: DataStorePreference
) : DataStoreRepository {
    override suspend fun saveDarkModeState(isDarkMode: String) {
        dataStorePreference.saveDarkModeState(isDarkMode)
    }

    override val readDarkModeState: Flow<String>
        get() = dataStorePreference.readDarkModeState

    override suspend fun saveStartTime(startTimeMillis: Long) {
        dataStorePreference.saveStartTime(startTimeMillis)
    }

    override suspend fun getStartTime(): Long? {
        return dataStorePreference.getStartTime()
    }

    override suspend fun clearStartTime() {
        dataStorePreference.clearStartTime()
    }

    override suspend fun setCountTimeFinished(isFinished: Boolean) {
        dataStorePreference.setCountTimeFinished(isFinished)
    }

    override suspend fun isCountTimeFinished(): Boolean {
        return dataStorePreference.isCountTimeFinished()
    }
}