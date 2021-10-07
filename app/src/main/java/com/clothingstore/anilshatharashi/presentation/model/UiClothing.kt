package com.clothingstore.anilshatharashi.presentation.model

data class UiClothingModel(
    var clothingList: List<UiClothing>,
    var meta: UiMeta
)

data class UiMeta(
    var limit: Int = 0,
    var last_offset_id: String = "",
    var end: Boolean = false
)

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
    var picturesData: List<UiPictures> = emptyList(),
    var priceAmount: Double = 0.0,
    var priceCurrency: String = "",
    var publishedDate: String = "",
    var createdDate: String = "",
    var purchaseViaPaypal: Boolean = false,
    var slug: String? = "",
    var variantSet: Int = 0,
    var variants: Map<String, Int> = emptyMap(),
    var handDelivery: Boolean = false,
    var userData: UiUser = UiUser(),
    var brandId: Int = 0,
)

data class UiPictures(
    var id: Int = 0,
    var formats: Map<String, UiPictureDimensions> = emptyMap()
)

data class UiPictureDimensions(
    var url: String = "",
    var height: Int = 0,
    var width: Int = 0
)

data class UiUser(var id: Int = 0)
