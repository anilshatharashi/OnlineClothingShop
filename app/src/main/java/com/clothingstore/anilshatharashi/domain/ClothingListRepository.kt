package com.clothingstore.anilshatharashi.domain

import kotlinx.coroutines.flow.Flow

interface ClothingListRepository {

    suspend fun getClothingList(): Flow<ClothingList?>

}
