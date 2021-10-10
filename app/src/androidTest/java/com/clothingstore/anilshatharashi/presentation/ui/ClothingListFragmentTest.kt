package com.clothingstore.anilshatharashi.presentation.ui

import android.content.Context
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.clothingstore.anilshatharashi.R
import com.clothingstore.anilshatharashi.presentation.ErrorFetchingClothingListData
import com.clothingstore.anilshatharashi.presentation.NoInternet
import com.clothingstore.anilshatharashi.presentation.UnknownException
import com.clothingstore.anilshatharashi.presentation.ui.ClothingListAdapter.ClothingItemViewHolder
import com.clothingstore.anilshatharashi.utils.RecyclerViewChildActions.Companion.actionOnChild
import com.clothingstore.anilshatharashi.utils.RecyclerViewChildActions.Companion.childOfViewAtPositionWithMatcher
import com.clothingstore.anilshatharashi.utils.launchClothingListActivity
import com.clothingstore.anilshatharashi.utils.launchFragmentInHiltContainer
import com.clothingstore.anilshatharashi.utils.mockUiClothingModel
import com.clothingstore.anilshatharashi.utils.runOnUiThread
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class ClothingListFragmentTest {

    private lateinit var fragment: ClothingListFragment

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        hiltRule.inject()
        launchClothingListActivity()

        val arguments = bundleOf("width" to 320)
        fragment =
            launchFragmentInHiltContainer<ClothingListFragment>(arguments) as ClothingListFragment
    }

    @Test
    fun test_show_loading_view_shows_progress_bar() {
        context.runOnUiThread { fragment.showLoadingView() }

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        onView(withId(R.id.clothingListRecyclerView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun clothingListItems_displayed_in_list() {
        val uiClothingModel = mockUiClothingModel()

        context.runOnUiThread { fragment.showContentView(uiClothingModel) }

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))

        onView(withId(R.id.clothingListRecyclerView))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.descriptionView,
                0,
                withText("This is a Beautiful Shirt"))))

        onView(withId(R.id.clothingListRecyclerView))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.activeStatusView,
                1,
                withText("Active"))))
        onView(withId(R.id.clothingListRecyclerView))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.priceAmountView,
                3,
                withText("Price : 24€"))))

        onView(withId(R.id.clothingListRecyclerView)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(3,
                actionOnChild(ViewActions.click(), R.id.descriptionView)))

        onView(withId(R.id.clothingListRecyclerView))
            .perform(actionOnItemAtPosition<ClothingItemViewHolder>(4, scrollTo()))
        onView(withId(R.id.clothingListRecyclerView))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.addressView,
                4,
                withText("Russelshiem, Germany"))))

        onView(withId(R.id.clothingListRecyclerView))
            .perform(actionOnItemAtPosition<ClothingItemViewHolder>(7, scrollTo()))
        onView(withId(R.id.clothingListRecyclerView))
            .check(matches(childOfViewAtPositionWithMatcher(R.id.publishedDateView,
                7,
                withText("Published on : 2021-10-08"))))
    }

    @Test
    fun showErrorMessage_displays_correct_message() {
        context.runOnUiThread { fragment.showErrorMessage("Error in Fetching the ClothingList") }
        onView(withText("Error in Fetching the ClothingList"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun handleFailure_displays_the_appropriate_ErrorMessage() {
        context.runOnUiThread { fragment.handleFailure(NoInternet) }
        onView(withText("Please check your Internet connection"))
            .check(matches(isDisplayed()))

        context.runOnUiThread { fragment.handleFailure(ErrorFetchingClothingListData) }
        onView(withText("Error in Fetching the ClothingList"))
            .check(matches(isDisplayed()))

        context.runOnUiThread { fragment.handleFailure(UnknownException) }
        onView(withText("Unknown Error"))
            .check(matches(isDisplayed()))
    }

}