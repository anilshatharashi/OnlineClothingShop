package com.clothingstore.anilshatharashi.data.repository

import com.clothingstore.anilshatharashi.data.repository.remote.ClothingListRemoteDataSource
import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.ClothingListRepository
import com.clothingstore.anilshatharashi.domain.mapper.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClothingListRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClothingListRemoteDataSource,
    private val mapper: Mapper<ClothingListResponse?, ClothingList?>
) : ClothingListRepository {

    override suspend fun getClothingList(): Flow<ClothingList?> =
        remoteDataSource.fetchClothingListData().map {
            mapper.mapFrom(it)
        }
}