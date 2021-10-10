package com.clothingstore.anilshatharashi.presentation.ui

import android.content.Context
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.clothingstore.anilshatharashi.utils.getSelectedUiClothing
import com.clothingstore.anilshatharashi.utils.launchClothingListActivity
import com.clothingstore.anilshatharashi.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class ClothingDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        hiltRule.inject()
    }

    @Test
    fun clothingDetails_displays_correct_data() {
        val uiClothing = getSelectedUiClothing()
        launchClothingListActivity()

        val arguments = bundleOf("selected_clothing" to uiClothing)
        val fragment: ClothingDetailFragment =
            launchFragmentInHiltContainer<ClothingDetailFragment>(arguments) as ClothingDetailFragment

        onView(withText("Description : A Shirt")).check(matches(isDisplayed()))
        onView(withText("Shipping International : 10€")).check(matches(isDisplayed()))
        onView(withText("Shipping National : 6€")).check(matches(isDisplayed()))
        onView(withText("Address : Frankfurt am Main, Germany")).check(matches(isDisplayed()))
        onView(withText("Price : 45€")).check(matches(isDisplayed()))
        onView(withText("Hand delivery : false")).check(matches(isDisplayed()))
    }
}