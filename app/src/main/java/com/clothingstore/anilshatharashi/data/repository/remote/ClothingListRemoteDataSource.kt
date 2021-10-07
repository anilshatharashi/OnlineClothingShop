package com.clothingstore.anilshatharashi.data.repository.remote

import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import kotlinx.coroutines.flow.Flow

interface ClothingListRemoteDataSource {

    suspend fun fetchClothingListData(): Flow<ClothingListResponse>
}
