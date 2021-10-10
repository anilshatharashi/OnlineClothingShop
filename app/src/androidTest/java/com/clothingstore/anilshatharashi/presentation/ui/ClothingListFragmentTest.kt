package com.clothingstore.anilshatharashi.presentation.ui

import android.content.Context
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.clothingstore.anilshatharashi.ClothingListActivity
import com.clothingstore.anilshatharashi.R
import com.clothingstore.anilshatharashi.presentation.ui.ClothingListAdapter.ClothingItemViewHolder
import com.clothingstore.anilshatharashi.utils.RecyclerViewChildActions.Companion.childOfViewAtPositionWithMatcher
import com.clothingstore.anilshatharashi.utils.launchFragmentInHiltContainer
import com.clothingstore.anilshatharashi.utils.mockUiClothingModel
import com.clothingstore.anilshatharashi.utils.runOnUiThread
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class ClothingListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        hiltRule.inject()
    }

    @Test
    fun clothingListItems_displayed_in_list() {
        val uiClothingModel = mockUiClothingModel()
        launchClothingListActivity()

        val arguments = bundleOf("width" to 320)
        val fragment: ClothingListFragment =
            launchFragmentInHiltContainer<ClothingListFragment>(arguments) as ClothingListFragment

        context.runOnUiThread { fragment.showContentView(uiClothingModel) }

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
                withText("Price: 24€"))))
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

    private fun launchClothingListActivity(): ActivityScenario<ClothingListActivity>? {
        val activityScenario = launch(ClothingListActivity::class.java)
        activityScenario.onActivity { activity ->
            (activity.findViewById(R.id.clothingListRecyclerView) as RecyclerView).itemAnimator =
                null
        }
        return activityScenario
    }
}