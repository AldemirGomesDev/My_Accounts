package br.com.aldemir.data.database.preference.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import br.com.aldemir.data.database.preference.createPreferencesDataStore
import br.com.aldemir.data.database.preference.getDataStorePreferenceFile
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import java.io.File

/**
 * Koin DI extension function to create a DataStore instance with a specific name.
 *
 * @param name The name of the DataStore file.
 * @return A Koin module with the DataStore instance.
 */
actual fun Module.preferencesDataStoreDI(name: String): KoinDefinition<DataStore<Preferences>> {
    return single(named(name)) {
        createPreferencesDataStore {
            val file = File(System.getProperty("java.io.tmpdir"), getDataStorePreferenceFile(name))
            file.absolutePath
        }
    }
}