package com.clothingstore.anilshatharashi.presentation.mapper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.clothingstore.anilshatharashi.domain.Clothing
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.PictureDimensions
import com.clothingstore.anilshatharashi.domain.Pictures
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatter
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatterImpl
import com.clothingstore.anilshatharashi.presentation.model.UiClothing
import com.clothingstore.anilshatharashi.presentation.model.UiClothingModel
import com.clothingstore.anilshatharashi.presentation.model.UiMeta
import com.clothingstore.anilshatharashi.presentation.model.UiPictures
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ClothingListUiMapperTest {

    private lateinit var dateFormatter: DateFormatter
    private lateinit var uiMapper: ClothingListUiMapper

    @get:Rule
    val taskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dateFormatter = DateFormatterImpl(mockk())
        uiMapper = ClothingListUiMapper(dateFormatter)
    }

    @Test
    fun `UiClothingModel should contain correct thumbnailAndCoverUrls for the SMALL PHONE`() {
        //Given ClothingList
        val uiClothingModel1 = UiClothingModel(
            listOf(UiClothing(id = 1, uiPictures = listOf(
                UiPictures(id = 12, thumbnailAndCoverPhotoUrls = Pair("url1", "url2")
                ))), UiClothing(id = 2, uiPictures = listOf(
                UiPictures(id = 123, thumbnailAndCoverPhotoUrls = Pair("urlxyz1", "urlxyz2"))))
            ), meta = UiMeta()
        )

        //When
        // SMALL PHONE
        uiMapper.smallestWidth = 320
        val result = uiMapper.mapFrom(getMockedClothingList())
        //Then
        assertEquals(uiClothingModel1, result)
    }

    @Test
    fun `UiClothingModel should contain correct thumbnailAndCoverUrls for the SEVEN INCH TAB`() {
        //Given ClothingList
        val clothingModel = UiClothingModel(
            listOf(UiClothing(id = 1, uiPictures = listOf(
                UiPictures(id = 12, thumbnailAndCoverPhotoUrls = Pair("url2", "url234")
                ))), UiClothing(id = 2, uiPictures = listOf(
                UiPictures(id = 123, thumbnailAndCoverPhotoUrls = Pair("urlxyz2", "urlxyz234"))))
            ), meta = UiMeta()
        )
        //When
        // 7 Inch Tablet
        uiMapper.smallestWidth = 720
        val result2 = uiMapper.mapFrom(getMockedClothingList())
        //Then
        assertEquals(clothingModel, result2)
    }

    @Test
    fun `UiClothingModel should NOT contain any thumbnailAndCoverUrls when DeviceSize is NOT set`() {
        //Given ClothingList
        val clothingModel = UiClothingModel(
            listOf(UiClothing(id = 1, uiPictures = listOf(
                UiPictures(id = 12, thumbnailAndCoverPhotoUrls = Pair(null, null)
                ))), UiClothing(id = 2, uiPictures = listOf(
                UiPictures(id = 123, thumbnailAndCoverPhotoUrls = Pair(null, null))))
            ), meta = UiMeta()
        )
        //When
        // 7 Inch Tablet
        uiMapper.smallestWidth = 0
        val result = uiMapper.mapFrom(getMockedClothingList())
        //Then
        assertEquals(clothingModel, result)
    }

    private fun getMockedClothingList() = ClothingList(
        listOf(
            Clothing(id = 1, pictures = listOf(
                Pictures(id = 12, formats = mapOf(
                    "P1" to PictureDimensions("url1", 320, 320),
                    "P2" to PictureDimensions(url = "url2", width = 480, height = 480),
                    "P3" to PictureDimensions(url = "url123", width = 640, height = 640),
                    "P4" to PictureDimensions(url = "url234", width = 1280, height = 1280))))),
            Clothing(id = 2, pictures = listOf(
                Pictures(id = 123, formats = mapOf(
                    "P1" to PictureDimensions(url = "urlxyz1", width = 320, height = 320),
                    "P2" to PictureDimensions(url = "urlxyz2", width = 480, height = 480),
                    "P3" to PictureDimensions(url = "urlxyz123", width = 640, height = 640),
                    "P4" to PictureDimensions(url = "urlxyz234", width = 1280, height = 1280)))))
        ))
}