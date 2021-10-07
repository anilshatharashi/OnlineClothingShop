package com.clothingstore.anilshatharashi.data.repository.remote

import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import retrofit2.http.GET


interface ClothingListApi {

    @GET("/api/v1/products/popular/?offset_id=")
    suspend fun fetchPopularClothingListData(): ClothingListResponse
}
