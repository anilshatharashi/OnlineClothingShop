package com.clothingstore.anilshatharashi.presentation.mapper

import android.util.Log
import com.clothingstore.anilshatharashi.domain.Clothing
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.PictureDimensions
import com.clothingstore.anilshatharashi.domain.Pictures
import com.clothingstore.anilshatharashi.domain.mapper.Mapper
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatter
import com.clothingstore.anilshatharashi.presentation.model.*
import javax.inject.Inject

class ClothingListUiMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
) : Mapper<ClothingList?, UiClothingModel> {

    var smallestWidth: Int = 0

    override fun mapFrom(from: ClothingList?): UiClothingModel {
        return UiClothingModel(
            mapClothingList(from?.clothingList ?: emptyList()),
            UiMeta(
                from?.meta?.limit ?: 0,
                from?.meta?.lastOffsetId ?: "",
                from?.meta?.end ?: false
            )
        )
    }

    private fun mapClothingList(clothingList: List<Clothing>) =
        clothingList.map {
            UiClothing(
                it.id,
                it.userId,
                it.activeStatus ?: "",
                it.status ?: "",
                it.address ?: "",
                it.categories ?: emptyList(),
                it.country ?: "",
                it.description ?: "",
                it.internationalShippingCost ?: "",
                it.nationalShippingCost ?: "",
                mapPictures(it.pictures ?: emptyList()),
                it.price_amount,
                it.price_currency ?: "",
                dateFormatter.formatToStringDateTime(it.pubDate),
                dateFormatter.formatToStringDateTime(it.createdDate),
                it.purchaseViaPaypal,
                it.slug,
                it.variantSet,
                it.variants ?: emptyMap(),
                it.handDelivery,
                UiUser(it.userData?.id ?: 0),
                it.brandId,
            )
        }

    private fun mapPictures(pictures: List<Pictures>): List<UiPictures> = pictures.map {
        UiPictures(it.id ?: 0, mapFormats(it.formats))
    }

    private fun mapFormats(formats: Map<String, PictureDimensions>?): Pair<String?, String?> {
        val photoSizes = getThumbnailAndCoverPhotoSizes()
        var thumbnailUrl: String? = null
        var covertPhotoUrl: String? = null
        formats?.forEach { (_, value) ->
            if (photoSizes.first == value.width) // taking less size as it is for thumbnail
                thumbnailUrl = value.url
            if (photoSizes.second == value.width)
                covertPhotoUrl = value.url
        }
        Log.i("**", "thumb=$thumbnailUrl")
        Log.i("**", "cover=$covertPhotoUrl")
        return Pair(thumbnailUrl, covertPhotoUrl)
    }

    private fun getThumbnailAndCoverPhotoSizes(): Pair<Int, Int> {
        //320dp: a typical phone screen (240x320 ldpi, 320x480 mdpi, 480x800 hdpi, etc).
        //480dp: a large phone screen ~5" (480x800 mdpi).
        //600dp: a 7” tablet (600x1024 mdpi).
        //720dp: a 10” tablet (720x1280 mdpi, 800x1280 mdpi, etc).
        var thumbnailSize = 0
        var coverPhotoSize = 0
        when (smallestWidth) {
            in 320..480 -> {
                thumbnailSize = 320; coverPhotoSize = 480
            }
            in 480..599 -> {
                thumbnailSize = 480; coverPhotoSize = 640
            }
            in 600..719 -> {
                thumbnailSize = 480; coverPhotoSize = 960
            }
            in 720..1280 -> {
                thumbnailSize = 480; coverPhotoSize = 1280
            }
        }
        return Pair(thumbnailSize, coverPhotoSize)
    }
}

