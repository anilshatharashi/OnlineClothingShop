package com.clothingstore.anilshatharashi.data.mapper

import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingData
import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import com.clothingstore.anilshatharashi.data.repository.remote.model.PictureInfo
import com.clothingstore.anilshatharashi.data.repository.remote.model.PicturesData
import com.clothingstore.anilshatharashi.domain.*
import com.clothingstore.anilshatharashi.domain.mapper.Mapper

class ClothingListDomainMapper : Mapper<ClothingListResponse?, ClothingList?> {
    override fun mapFrom(from: ClothingListResponse?): ClothingList =
        ClothingList(
            mapClothingList(from?.clothingList),
            from?.metaData?.let { Meta(it.limit, it.last_offset_id, it.end) }
        )

    private fun mapClothingList(clothingList: List<ClothingData>?) =
        clothingList?.map {
            Clothing(
                it.id,
                it.user_id,
                it.active_status,
                it.status,
                it.address,
                it.categories,
                it.country,
                it.description,
                it.international_shipping_cost,
                it.national_shipping_cost,
                mapPictures(it.pictures_data),
                it.price_amount,
                it.price_currency,
                it.pub_date,
                it.created_date,
                it.purchase_via_paypal,
                it.slug,
                it.variant_set,
                it.variants,
                it.hand_delivery,
                User(it.user_data?.id ?: 0),
                it.brand_id,
            )
        }

    private fun mapPictures(picturesData: List<PicturesData>?): List<Pictures> {
        val pictures = mutableListOf<Pictures>()
        picturesData?.let { picture ->
            picture.forEach { pictures.add(Pictures(it.id, mapToPictureDimensions(it.formats))) }
        }
        return pictures
    }

    private fun mapToPictureDimensions(map: Map<String, PictureInfo>?): Map<String, PictureDimensions> {
        if (map == null) return emptyMap()
        val mutableMap: MutableMap<String, PictureDimensions> = HashMap()
        map.forEach { (key, value) ->
            mutableMap[key] = PictureDimensions(value.url, value.height, value.width)
        }
        return mutableMap
    }
}

