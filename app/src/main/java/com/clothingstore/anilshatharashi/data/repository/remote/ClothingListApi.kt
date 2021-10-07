package com.clothingstore.anilshatharashi.data.repository.remote

import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface ClothingListApi {

    @GET("/api/v1/products/popular/")
    suspend fun fetchPopularClothingListData(@Query("offset_id") offsetId: Int)
            : ClothingListResponse
}
