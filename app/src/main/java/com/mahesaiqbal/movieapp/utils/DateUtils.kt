package com.mahesaiqbal.movieapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat

fun dateFormat(dateString: String): String {
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")

    val date = inputFormat.parse(dateString)
    val outputDateStr = outputFormat.format(date!!)

    return outputDateStr
}