package br.com.aldemir.data.database.room

import android.util.Log
import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        if (value != null) {
            Log.d("Incoming_long", "Long $value To Date: ${Date(value)} ")
        }
        return if (value == null) null else Date(value)

    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        if (date != null) {
            Log.d("Incoming_long", "Date $date To Long: ${date.getTime()} ")
        }
        return if (date == null) null else date.getTime()
    }

}