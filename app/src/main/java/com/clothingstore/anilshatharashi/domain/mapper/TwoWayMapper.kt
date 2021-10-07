package com.clothingstore.anilshatharashi.domain.mapper

interface TwoWayMapper<T, U>: Mapper<T, U> {
    fun mapTo(from: U): T
}
