package br.com.aldemir.data.database.preference.extensions

import br.com.aldemir.data.database.preference.createPreferencesDataStore
import br.com.aldemir.data.database.preference.getDataStorePreferenceFile
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private const val DATASTORE_TAG = "DriverDataStore"

/**
 * Koin DI extension function to create a DataStore instance with a specific name.
 *
 * @param name The name of the DataStore file.
 * @return A Koin module with the DataStore instance.
 */
@OptIn(ExperimentalForeignApi::class)
actual fun Module.preferencesDataStoreDI(name: String) = single(named(name)) {
    createPreferencesDataStore {
        executeWithErrorHandling(DATASTORE_TAG) { errorPtr ->
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = errorPtr
            )
            val finalPath: Path = requireNotNull(directory?.path()).toPath() / getDataStorePreferenceFile(name)
            println("Creating DataStore at path: $finalPath")
            finalPath.toString()
        }
    }
}
