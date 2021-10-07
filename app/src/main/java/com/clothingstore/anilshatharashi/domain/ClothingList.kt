package com.clothingstore.anilshatharashi.domain

data class ClothingList(
    var clothingList: List<Clothing>? = null,
    var meta: Meta? = null
)

data class Meta(
    var limit: Int = 0,
    var lastOffsetId: String? = null,
    var end: Boolean = false
)

data class Clothing(
    var id: Int = 0,
    var userId: Int = 0,
    var activeStatus: String? = null,
    var status: String? = null,
    var address: String? = null,
    var categories: List<Int>? = null,
    var country: String? = null,
    var description: String? = null,
    var internationalShippingCost: String? = null,
    var nationalShippingCost: String? = null,
    var pictures: List<Pictures>? = null,
    var price_amount: Double = 0.0,
    var price_currency: String? = null,
    var pubDate: String? = null,
    var createdDate: String? = null,
    var purchaseViaPaypal: Boolean = false,
    var slug: String? = null,
    var variantSet: Int = 0,
    var variants: Map<String, Int>? = null,
    var handDelivery: Boolean = false,
    var userData: User? = null,
    var brandId: Int = 0,
)

data class Pictures(
    var id: Int? = null,
    var formats: Map<String, PictureDimensions>? = null
)

data class PictureDimensions(
    var url: String? = null,
    var height: Int? = null,
    var width: Int? = null
)

data class User(var id: Int = 0)
