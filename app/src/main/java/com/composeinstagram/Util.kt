package com.composeinstagram

import java.text.SimpleDateFormat
import java.util.*

fun Int.toCommaNumber(): String = "%,d".format(this)
fun Long.toInstaTime(multiply: Boolean = false): String {

    val min = 60000
    val hour = min * 60
    val aDay = hour * 24
    val towDay = aDay * 2
    val threeDay = aDay * 3
    val fourDay = aDay * 4
    val fiveDay = aDay * 5
    val sixDay = aDay * 6
    val sevenDay = aDay * 7
    val eightDay = aDay * 8

    val currentTime = System.currentTimeMillis()

    val time = if (multiply) this * 1000 else this
    val diff = currentTime - time
    return when {
        diff < aDay ->
            when {
                diff < min -> java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(diff)
                    .toString() +
                        " " + "second ago"
                diff < hour -> java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(diff)
                    .toString() +
                        " " + "minutes ago"
                else -> java.util.concurrent.TimeUnit.MILLISECONDS.toHours(diff).toString() +
                        " " + "hours ago"
            }
        diff in aDay until towDay - 1 -> "1 day ago"
        diff in towDay until threeDay - 1 -> "2 days ago"
        diff in threeDay until fourDay - 1 -> "3 days ago"
        diff in fourDay until fiveDay - 1 -> "4 days ago"
        diff in fiveDay until sixDay - 1 -> "5 days ago"
        diff in sixDay until sevenDay - 1 -> "6 days ago"
        diff in sevenDay until eightDay - 1 -> "7 days ago"
        else -> Date(this).toTime("MM-dd")
    }

}

fun Date.toTime(outputPatten: String = "yyyy-MM-dd"): String {
    val simpleDataFormat = SimpleDateFormat(outputPatten, Locale.getDefault())
    return simpleDataFormat.format(this)
}