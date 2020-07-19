package com.andreh.chatmockapp.utils

import java.util.*

fun ClosedRange<Char>.randomString(length: Int) =
    (1..length)
        .map { (Random().nextInt(endInclusive.toInt() - start.toInt()) + start.toInt()).toChar() }
        .joinToString("")