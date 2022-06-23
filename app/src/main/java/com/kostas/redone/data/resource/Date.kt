package com.kostas.redone.data.resource

import java.text.SimpleDateFormat
import java.util.*

fun getPastAndCurrentMonth(): Pair<String, String> {
    val currentMonth = SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)

    val pastMonth = SimpleDateFormat("dd/MM/yyyy").format(
        Calendar.getInstance().apply {
            add(Calendar.MONTH, -1)
        }.time
    )
    return pastMonth to currentMonth
}

fun getCurrentTime(): String {
    return reverseDate(SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time))
}

fun reverseDate(date: String?): String {
    val dateStringBuilder = StringBuilder()
    val splitDate = date?.split(".")?.reversed()
    splitDate?.forEach {
        if (splitDate.indexOf(it) == 2) {
            dateStringBuilder.append(it)
        } else {
            dateStringBuilder.append("$it.")
        }
    }
    return dateStringBuilder.toString()
}