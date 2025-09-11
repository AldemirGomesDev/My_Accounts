package br.com.aldemir.data.database.preference.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module

/**
 * Koin DI extension function to create a DataStore instance with a specific name.
 *
 * @param name The name of the DataStore file.
 * @return A Koin module with the DataStore instance.
 */
expect fun Module.preferencesDataStoreDI(name: String): KoinDefinition<DataStore<Preferences>>