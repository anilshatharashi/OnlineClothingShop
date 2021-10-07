package com.clothingstore.anilshatharashi.presentation

import com.clothingstore.anilshatharashi.presentation.model.UiClothingModel

sealed class ClothingListState {
    object Loading : ClothingListState()
    data class Success(val uiModel: UiClothingModel) : ClothingListState()
    data class Failure(val exception: Exception) : ClothingListState()
}

object NoInternet : Exception()
object ErrorFetchingClothingListData : Exception()
object UnknownException : Exception()
