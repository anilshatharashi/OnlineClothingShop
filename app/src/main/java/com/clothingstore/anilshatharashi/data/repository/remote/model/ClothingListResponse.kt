package com.clothingstore.anilshatharashi.data.repository.remote.model

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime
import java.util.*

data class ClothingListResponse(
    @SerializedName("objects")
    var clothingList: List<ClothingData>? = null,
    @SerializedName("meta")
    var metaData: MetaData? = null
)

data class MetaData(
    var limit: Int = 0,
    var last_offset_id: String? = null,
    var end: Boolean = false
)

data class ClothingData(
    var id: Int = 0,
    var user_id: Int = 0,
    var active_status: String? = null,
    var status: String? = null,
    var address: String? = null,
    var categories: List<Int>? = null,
    var country: String? = null,
    var description: String? = null,
    var international_shipping_cost: String? = null,
    var national_shipping_cost: String? = null,
    @SerializedName("pictures_data")
    var pictures_data: List<PicturesData>? = null,
    var price_amount: Double = 0.0,
    var price_currency: String? = null,
    var pub_date: String? = null,
    var created_date: String? = null,
    var purchase_via_paypal: Boolean = false,
    var slug: String? = null,
    var variant_set: Int = 0,
    var variants: Map<String, Int>? = null,
    var hand_delivery: Boolean = false,
    var user_data: UserData? = null,
    var brand_id: Int = 0,
    var videos: List<Video?>? = null
)

data class PicturesData(
    var id: Int? = null,
    @SerializedName("formats")
    var formats: Map<String, PictureInfo>? = null
)

data class PictureInfo(
    var url: String? = null,
    var height: Int? = null,
    var width: Int? = null
)

data class UserData(var id: Int = 0)

data class Video(
    var id: String? = null,
    var videoUrl: String? = null,
    var outputs: List<Output> = ArrayList(),
    var thumbnail: Thumbnail? = null,
)

data class Thumbnail(
    @SerializedName("formats")
    var formats: Map<String, PictureInfo>? = null
)

data class Output(var url: String? = null, var format: String? = null)
