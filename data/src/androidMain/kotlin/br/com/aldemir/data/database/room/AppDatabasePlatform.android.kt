package br.com.aldemir.data.database.room

import androidx.room.Room
import org.koin.mp.KoinPlatform

actual fun databaseInstance(): ConfigDatabase {
    return Room.databaseBuilder(
        KoinPlatform.getKoin().get(),
        ConfigDatabase::class.java,
        dbFileName
    ).build()
}