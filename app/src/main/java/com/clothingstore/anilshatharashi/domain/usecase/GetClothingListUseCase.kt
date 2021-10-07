package com.clothingstore.anilshatharashi.domain.usecase

import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.ClothingListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClothingListUseCase @Inject constructor(private val repository: ClothingListRepository) :
    UseCase<Int, ClothingList?>() {

    override suspend fun buildUseCase(parms: Int): Flow<ClothingList?> =
        repository.getClothingList(parms)
}
