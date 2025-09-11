package br.com.aldemir.data.database.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

private const val PREFERENCE_KEY = "app_dark_mode_state"

class DataStorePreferenceImpl(
    private val dataStore: DataStore<Preferences>
) : DataStorePreference {

    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
    }


    override suspend fun saveDarkModeState(isDarkMode: String) {
        dataStore.edit {preference ->
            preference[PreferenceKeys.sortKey] = isDarkMode
        }
    }

    override val readDarkModeState: Flow<String> = dataStore.data
        .catch {exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {preferences ->
            val sortState = preferences[PreferenceKeys.sortKey]
            sortState ?: ""
        }
}