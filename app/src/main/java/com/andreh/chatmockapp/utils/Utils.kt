package com.andreh.chatmockapp.utils

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