package br.com.aldemir.common.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object DateUtils {

    fun getYear() : String {
        val date = Calendar.getInstance()
        return date.get(Calendar.YEAR).toString()
    }

    fun getMonth() : String {
        val date = Calendar.getInstance()
        val sdf = SimpleDateFormat("MMMM", Locale.getDefault())
        return sdf.format(date.time).uppercase()
    }

    fun getDate(): Date {
        return Calendar.getInstance().time
    }

    fun getSixMonthsPrevious(): MutableList<String> {
        val sdf = SimpleDateFormat("MMMM", Locale.getDefault())
        val months = arrayListOf<String>()

        for (item in -6 until 0) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, item)
            cal.set(Calendar.DAY_OF_MONTH, 1)
            val month = sdf.format(cal.time).uppercase()
            months.add(month)
        }
        return months
    }

    fun getYearsFromSixMonthsPrevious(): MutableList<String> {
        val years = arrayListOf<String>()

        for (item in -6 until 0) {
           val cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, item)
            years.add(cal.get(Calendar.YEAR).toString())
        }
        return years
    }

    fun getMonths(amountOfTimes: Int): MutableList<String> {
        var cal: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("MMMM", Locale.getDefault())
        val months = arrayListOf<String>()
        months.add(sdf.format(cal.time).uppercase())

        for (item in 1 until amountOfTimes) {
            cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, item)
            val month = sdf.format(cal.time).uppercase()
            months.add(month)
        }
        return months
    }

    fun getYears(amountOfTimes: Int): MutableList<String> {
        var cal: Calendar = Calendar.getInstance()
        val years = arrayListOf<String>()
        years.add(cal.get(Calendar.YEAR).toString())

        for (item in 1 until amountOfTimes) {
            cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, item)
            years.add(cal.get(Calendar.YEAR).toString())
        }
        return years
    }

    fun getDay(): Int {
        val cal: Calendar = Calendar.getInstance()
        return cal.get(Calendar.DAY_OF_MONTH)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLastSixMonths(): List<LocalDate> {
        val currentDate = LocalDate.now()
        val lastSixMonths = mutableListOf<LocalDate>()
        for (i in 1..6) {
            lastSixMonths.add(currentDate.minusMonths(i.toLong()))
        }
        return lastSixMonths
    }
}