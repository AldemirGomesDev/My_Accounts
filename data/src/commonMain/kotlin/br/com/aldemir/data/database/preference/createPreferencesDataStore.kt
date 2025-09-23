package br.com.aldemir.data.database.preference

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath

fun createPreferencesDataStore(producePath: () -> String) = PreferenceDataStoreFactory.createWithPath {
    producePath().toPath()
}

fun getDataStorePreferenceFile(name: String): String {
    return if (name.endsWith(".preferences_pb")) {
        name
    } else {
        "$name.preferences_pb"
    }
}
