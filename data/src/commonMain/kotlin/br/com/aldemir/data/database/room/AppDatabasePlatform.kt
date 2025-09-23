package br.com.aldemir.data.database.room

import androidx.room.RoomDatabaseConstructor


@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ConfigDatabase> {
    override fun initialize(): ConfigDatabase
}

expect fun databaseInstance(): ConfigDatabase

internal const val dbFileName = "AccountDataBase"