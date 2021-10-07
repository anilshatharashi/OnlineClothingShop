package com.clothingstore.anilshatharashi.data.repository.remote

import android.content.Context
import com.google.gson.Gson
import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import com.clothingstore.anilshatharashi.platform.readJsonFromAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
/*

// This implementation would be replaced by Retrofit Implementation when we use the Actual Network call
class ClothingListApiImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) : ClothingListApi {

    override suspend fun fetchClothingListData(): Flow<ClothingListResponse> =
        flow { emit(readJsonFromAssets(context, gson, MOCK_RESPONSE_FILE_NAME)) }

    companion object {
        private const val MOCK_RESPONSE_FILE_NAME = "clothinglist_response.json"
    }
}*/
