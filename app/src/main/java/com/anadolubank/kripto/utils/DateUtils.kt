// ui/utils/DateUtils.kt
package com.anadolubank.kripto.ui.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

internal val isoDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

/**
 * @return milliseconds since epoch for a string like "2025-07-14"
 */
fun parseIsoDateToMillis(date: String): Long =
    isoDateFormat.parse(date)!!.time

/*

package com.anadolubank.kripto.ui.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

// Get the default locale of the device
internal val deviceLocale: Locale = Locale.getDefault()

// Get the default timezone of the device
internal val deviceTimeZone: TimeZone = TimeZone.getDefault()

internal val isoDateFormat = SimpleDateFormat("yyyy-MM-dd", deviceLocale).apply {
    timeZone = deviceTimeZone
}

/**
 * @return milliseconds since epoch for a string like "2025-07-14"
 */
fun parseIsoDateToMillis(date: String): Long =
    isoDateFormat.parse(date)!!.time

*/