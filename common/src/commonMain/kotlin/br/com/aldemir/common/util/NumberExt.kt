package br.com.aldemir.common.util

fun formatTwoDigits(number: Int): String = if (number < 10) "0$number" else "$number"

expect fun Float.toPercentFormatted(): String
