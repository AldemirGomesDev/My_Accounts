package br.com.aldemir.data.database.preference.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import br.com.aldemir.data.database.preference.createPreferencesDataStore
import br.com.aldemir.data.database.preference.getDataStorePreferenceFile
import org.koin.core.module.Module

/**
 * Koin DI extension function to create a DataStore instance with a specific name.
 *
 * @param name The name of the DataStore file.
 * @return A Koin module with the DataStore instance.
 */

actual fun Module.preferencesDataStoreDI(name: String) = single<DataStore<Preferences>> {
    createPreferencesDataStore {
        get<Context>().dataStoreFile(getDataStorePreferenceFile(name)).absolutePath
    }
}
