package com.clothingstore.anilshatharashi.presentation.mapper.parsers

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateFormatterImpl @Inject constructor(private val dateFormat: DateFormat) : DateFormatter {

    override fun formatToStringDateTime(givenDateTime: String?): String {
        if (givenDateTime == null) return ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date: Date = simpleDateFormat.parse(givenDateTime) ?: return ""
        return dateFormat.format(date)
    }

}