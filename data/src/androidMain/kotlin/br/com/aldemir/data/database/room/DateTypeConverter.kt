package br.com.aldemir.data.database.room

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class DateTypeConverter {

    @TypeConverter
    fun fromInstant(value: Instant?): Long? = value?.toEpochMilliseconds()

    @TypeConverter
    fun toInstant(value: Long?): Instant? = value?.let { Instant.fromEpochMilliseconds(it) }

}