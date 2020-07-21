package com.andreh.chatmockapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.andreh.chatmockapp.data.db.users.UserWithMessages
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun ClosedRange<Char>.randomString(length: Int) =
    (1..length)
        .map { (Random().nextInt(endInclusive.toInt() - start.toInt()) + start.toInt()).toChar() }
        .joinToString("")

fun randomDelay(min: Float, max: Float) {
    val random = (max * Math.random() + min).toInt()
    try {
        Thread.sleep(random * 1000.toLong())
    } catch (e: InterruptedException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
val dateTimeStrToLocalDateTime: (UserWithMessages) -> LocalDate = {
    if (it.messages.size > 0){
        LocalDate.parse(it.messages[it.messages.size-1].timestamp, DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm:ss"))
    }
    LocalDate.now()
}