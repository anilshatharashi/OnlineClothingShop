package com.clothingstore.anilshatharashi.domain.mapper

interface Mapper<T, U> {
    fun mapFrom(from: T): U
}
