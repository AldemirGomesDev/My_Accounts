package br.com.aldemir.common.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

object DateUtils {

    fun getYearString(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(timeZone)
        return localDateTime.year.toString()
    }

    fun getMonthString(timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(timeZone)
        return localDateTime.month.name.uppercase()
    }

    fun getCurrentDate(): Instant {
        return Clock.System.now()
    }

    fun getCurrentDay(): Int {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        return localDateTime.dayOfMonth
    }

    fun getSixMonthsPrevious(): MutableList<String> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val months = mutableListOf<String>()
        for (item in -5..0) {
            val date = now.date.minus(DatePeriod(months = (-item)))
            months.add(date.month.name.uppercase())
        }
        return months
    }

    fun getYearsFromSixMonthsPrevious(): MutableList<String> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val years = mutableListOf<String>()
        for (item in -5..0) {
            val date = now.minus(DatePeriod(months = -item))
            years.add(date.year.toString())
        }
        return years
    }

    fun getMonths(amountOfTimes: Int): MutableList<String> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val months = mutableListOf<String>()
        for (item in 0 until amountOfTimes) {
            val date = now.plus(DatePeriod(months = item))
            months.add(date.month.name.uppercase())
        }
        return months
    }

    fun getYears(amountOfTimes: Int): MutableList<String> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val years = mutableListOf<String>()
        for (item in 0 until amountOfTimes) {
            val date = now.plus(DatePeriod(months = item))
            years.add(date.year.toString())
        }
        return years
    }
}