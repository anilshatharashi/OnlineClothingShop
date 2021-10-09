package com.clothingstore.anilshatharashi.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class UiClothingModel(
    var clothingList: List<UiClothing>,
    var meta: UiMeta
)

data class UiMeta(
    var limit: Int = 0,
    var last_offset_id: String = "",
    var end: Boolean = false
)
@Parcelize
data class UiClothing(
    var id: Int = 0,
    var userId: Int = 0,
    var activeStatus: String = "",
    var status: String = "",
    var address: String = "",
    var categories: List<Int> = emptyList(),
    var country: String = "",
    var description: String = "",
    var internationalShippingCost: String = "",
    var nationalShippingCost: String = "",
    var uiPictures: @RawValue List<UiPictures> = emptyList(),
    var priceAmount: Double = 0.0,
    var priceCurrency: String = "",
    var publishedDate: String = "",
    var createdDate: String = "",
    var purchaseViaPaypal: Boolean = false,
    var slug: String = "",
    var variantSet: Int = 0,
    var variants: Map<String, Int> = emptyMap(),
    var handDelivery: Boolean = false,
    var userData: @RawValue UiUser = UiUser(),
    var brandId: Int = 0,
) : Parcelable

data class UiPictures(
    var id: Int = 0,
    var thumbnailAndCoverPhotoUrls: Pair<String?, String?>,
)

data class UiUser(var id: Int = 0)
