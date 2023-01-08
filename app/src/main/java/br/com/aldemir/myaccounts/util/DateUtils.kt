package br.com.aldemir.myaccounts.util

import java.text.SimpleDateFormat
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

}