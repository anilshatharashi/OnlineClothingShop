package com.clothingstore.anilshatharashi.presentation.mapper.parsers

interface DateFormatter {

    fun formatToStringDateTime(givenDateTime: String?): String
}