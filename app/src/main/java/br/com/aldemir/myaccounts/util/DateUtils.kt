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
        val month = sdf.format(date.time).uppercase()
        return when (date.get(Calendar.MONTH)) {
            0 -> "1 - $month"
            1 -> "2 - $month"
            2 -> "3 - $month"
            3 -> "4 - $month"
            4 -> "5 - $month"
            5 -> "6 - $month"
            6 -> "7 - $month"
            7 -> "8 - $month"
            8 -> "9 - $month"
            9 -> "10 - $month"
            10 -> "11 - $month"
            11 -> "12 - $month"
            else -> ""
        }
    }

    fun getDate(): Date {
        return Calendar.getInstance().time
    }

}