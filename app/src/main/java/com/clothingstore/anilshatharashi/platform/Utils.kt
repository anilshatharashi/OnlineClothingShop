package com.clothingstore.anilshatharashi.platform

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

inline fun <reified T> readJsonFromAssets(context:Context, gson: Gson, fileName: String): T {
    val inputStream = context.assets.open(fileName)
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    return gson.fromJson(bufferedReader, T::class.java)
}