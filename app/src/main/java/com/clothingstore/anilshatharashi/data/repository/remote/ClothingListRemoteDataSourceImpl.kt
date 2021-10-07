package com.clothingstore.anilshatharashi.data.repository.remote

import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import com.clothingstore.anilshatharashi.platform.NetworkHandler
import com.clothingstore.anilshatharashi.presentation.NoInternet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClothingListRemoteDataSourceImpl @Inject constructor(
    private val networkHandler: NetworkHandler,
    private val clothingListApi: ClothingListApi,
) : ClothingListRemoteDataSource {

    override suspend fun fetchClothingListData(): Flow<ClothingListResponse> =
        flow {
            if (!networkHandler.isConnected()) throw NoInternet
            val response = clothingListApi.fetchPopularClothingListData()
            emit(response)
        }
}