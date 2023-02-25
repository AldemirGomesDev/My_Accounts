package br.com.aldemir.myaccounts.data.database.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import br.com.aldemir.myaccounts.util.Const.PREFERENCE_KEY
import br.com.aldemir.myaccounts.util.Const.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStorePreferenceImpl @Inject constructor(
    @ApplicationContext private val context: Context
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