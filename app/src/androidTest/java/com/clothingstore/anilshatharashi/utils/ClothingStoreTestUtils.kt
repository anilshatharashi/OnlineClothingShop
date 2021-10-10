package com.clothingstore.anilshatharashi.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import com.clothingstore.anilshatharashi.ClothingListActivity
import com.clothingstore.anilshatharashi.R
import com.clothingstore.anilshatharashi.presentation.model.UiClothing
import com.clothingstore.anilshatharashi.presentation.model.UiClothingModel
import com.clothingstore.anilshatharashi.presentation.model.UiMeta
import com.clothingstore.anilshatharashi.presentation.model.UiPictures


fun mockUiClothingModel(): UiClothingModel = UiClothingModel(
    listOf(
        UiClothing(id = 1,
            description = "This is a Beautiful Shirt",
            uiPictures = listOf(
                UiPictures(id = 12,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            activeStatus = "Active",
            address = "Frankfurt, Germany",
            priceAmount = 95.30,
            publishedDate = "2021-10-03"),
        UiClothing(id = 2,
            uiPictures = listOf(
                UiPictures(id = 13,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            description = "This is a Beautiful Top",
            activeStatus = "Active",
            address = "Frankfurt, Germany",
            priceAmount = 95.30,
            publishedDate = "2021-10-08"),
        UiClothing(id = 3,
            uiPictures = listOf(
                UiPictures(id = 14,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/5673340/1084771509_86561d3c6a514b28ae69f8365007ef06/P5.jpg",
                        "https://media-photos.depop.com/b0/5673340/1084771509_86561d3c6a514b28ae69f8365007ef06/P6.jpg"))),
            description = "A Shirt",
            activeStatus = "InActive",
            address = "Frankfurt am Main, Germany",
            priceAmount = 45.0,
            publishedDate = "2021-10-08"),
        UiClothing(id = 4,
            uiPictures = listOf(
                UiPictures(id = 15,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            description = "Pant ",
            activeStatus = "Active",
            address = "Munich, Germany",
            priceAmount = 24.0,
            publishedDate = "2021-10-03"),
        UiClothing(id = 5,
            uiPictures = listOf(
                UiPictures(id = 16,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            description = "This is a Beautiful Shirt",
            activeStatus = "Active",
            address = "Russelshiem, Germany",
            priceAmount = 84.90,
            publishedDate = "2021-10-03"),
        UiClothing(id = 6,
            uiPictures = listOf(
                UiPictures(id = 17,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            description = "This is a Beautiful Shirt",
            activeStatus = "Sold Out",
            address = "Rodelhiem, Frankfurt, Germany",
            priceAmount = 74.0,
            publishedDate = "2021-10-08"),
        UiClothing(id = 7,
            uiPictures = listOf(
                UiPictures(id = 1,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            description = "Beautiful vintage Chiara Ventura dress with floral print. All lined.\\nSize S/M\\n\\nFabric: 100%viscose\\nCondition: no signs of being overworn.\\n\\nThe blazer is also available for sale, if you need help finding it please drop a message.\\n\\nThe model is 5'8'' tall and size 6/8 for reference.\\n\\n\uD83C\uDF3FShipping from Romania\\n\uD83C\uDF3FAvailable shipping worldwide\\n\uD83C\uDF3FMessage before buying\\n\\nShipping prices worldwide tracked: \\n\\n0-150g - £4,80 \\n150-500g - £6,80 \\n500-700g - £8,80\\n700-1200g - £12,80 \\n1200-2000g - £17,80 \\nOver 2000g - starting £22,80 \\n\\n@depop #chiaraventura #vintage #print #floral #truevintage",
            activeStatus = "In Active",
            priceAmount = 24.0,
            publishedDate = "2021-10-08"),
        UiClothing(id = 8,
            uiPictures = listOf(
                UiPictures(id = 1,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            description = "Vintage Shrug",
            activeStatus = "Active",
            address = "Rodelhiem, Frankfurt, Germany",
            priceAmount = 43.0,
            publishedDate = "2021-10-08"),
        UiClothing(id = 9,
            uiPictures = listOf(
                UiPictures(id = 1,
                    thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P5.jpg",
                        "https://media-photos.depop.com/b0/19034247/1103214604_f2932893772e4b0abbfe6f478b9c8ca2/P6.jpg"))),
            description = "Vintage Shrug2",
            activeStatus = "Sold Out",
            address = "Rodelhiem, Germany",
            priceAmount = 84.0,
            publishedDate = "2021-10-08"),
    ), meta = UiMeta()
)


fun Context.runOnUiThread(f: Context.() -> Unit) {
    if (ContextHelper.mainThread == Thread.currentThread()) f() else ContextHelper.handler.post { f() }
}

private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
    val mainThread: Thread = Looper.getMainLooper().thread
}

fun launchClothingListActivity(): ActivityScenario<ClothingListActivity>? {
    val activityScenario = ActivityScenario.launch(ClothingListActivity::class.java)
    activityScenario.onActivity { activity ->
        (activity.findViewById(R.id.clothingListRecyclerView) as RecyclerView).itemAnimator =
            null
    }
    return activityScenario
}

fun getSelectedUiClothing() =
    UiClothing(id = 3,
        uiPictures = listOf(
            UiPictures(id = 14,
                thumbnailAndCoverPhotoUrls = Pair("https://media-photos.depop.com/b0/5673340/1084771509_86561d3c6a514b28ae69f8365007ef06/P5.jpg",
                    "https://media-photos.depop.com/b0/5673340/1084771509_86561d3c6a514b28ae69f8365007ef06/P6.jpg"))),
        description = "A Shirt",
        activeStatus = "InActive",
        address = "Frankfurt am Main, Germany",
        priceAmount = 45.0,
        handDelivery = false,
        internationalShippingCost = "10",
        nationalShippingCost = "6",
        publishedDate = "2021-10-08")
