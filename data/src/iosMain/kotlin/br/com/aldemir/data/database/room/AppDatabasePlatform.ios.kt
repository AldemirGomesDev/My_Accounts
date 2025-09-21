package br.com.aldemir.data.database.room

import androidx.room.Room
import androidx.room.util.findDatabaseConstructorAndInitDatabaseImpl
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory).path!!
}

actual fun databaseInstance(): ConfigDatabase {
    val dbFile = "${documentDirectory()}/$dbFileName"
    return Room.databaseBuilder<ConfigDatabase>(
        name = dbFile,
        factory = { findDatabaseConstructorAndInitDatabaseImpl(ConfigDatabase::class) }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}