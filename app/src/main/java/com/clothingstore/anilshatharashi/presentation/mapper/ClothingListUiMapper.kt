package com.clothingstore.anilshatharashi.presentation.mapper

import com.clothingstore.anilshatharashi.domain.Clothing
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.PictureDimensions
import com.clothingstore.anilshatharashi.domain.Pictures
import com.clothingstore.anilshatharashi.domain.mapper.Mapper
import com.clothingstore.anilshatharashi.presentation.model.*
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatter

class ClothingListUiMapper(
    private val dateFormatter: DateFormatter,
) : Mapper<ClothingList?, UiClothingModel> {

    override fun mapFrom(from: ClothingList?): UiClothingModel =
        UiClothingModel(
            mapClothingList(from?.clothingList ?: emptyList()),
            UiMeta(
                from?.meta?.limit ?: 0,
                from?.meta?.lastOffsetId ?: "",
                from?.meta?.end ?: false
            )
        )

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
        UiPictures(it.id ?: 0, mapToPictureDimensions(it.formats))
    }

    private fun mapToPictureDimensions(map: Map<String, PictureDimensions>?): Map<String, UiPictureDimensions> {
        if (map == null) return emptyMap()
        val mutableMap: MutableMap<String, UiPictureDimensions> = HashMap()
        map.forEach { (key, value) ->
            mutableMap[key] = UiPictureDimensions(
                value.url ?: "",
                value.height ?: 0, value.width ?: 0
            )
        }
        return mutableMap
    }
}

