package br.com.aldemir.data.database.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val PREFERENCE_NAME = "my_accounts_preferences"
private const val PREFERENCE_KEY = "dark_mode_state"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

class DataStorePreferenceImpl constructor(
    private val context: Context
) : DataStorePreference {

    private object PreferenceKeys {
        val sortKey = booleanPreferencesKey(name = PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveDarkModeState(isDarkMode: Boolean) {
        dataStore.edit {preference ->
            preference[PreferenceKeys.sortKey] = isDarkMode
        }
    }

    override val readDarkModeState: Flow<Boolean> = dataStore.data
        .catch {exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {preferences ->
            val sortState = preferences[PreferenceKeys.sortKey] ?: false
            sortState
        }
}