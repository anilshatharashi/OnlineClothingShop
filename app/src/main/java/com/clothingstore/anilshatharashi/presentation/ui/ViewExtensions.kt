package com.clothingstore.anilshatharashi.presentation.ui

import android.app.Activity
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.clothingstore.anilshatharashi.R

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String?) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment, tag)
        .addToBackStack(null)
        .commit()
}

fun FragmentActivity.addFragment(fragment: Fragment, tag: String?) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragment, tag)
        .commit()
}

fun Activity.getWidthAndHeightOfTheDevice(): Pair<Int, Int> {
    val screenSize = (resources.configuration.screenLayout
            and Configuration.SCREENLAYOUT_SIZE_MASK)
    when {
        screenSize >= Configuration.SCREENLAYOUT_SIZE_XLARGE -> {
            return Pair(first = 720, second = 960)
        }
        screenSize >= Configuration.SCREENLAYOUT_SIZE_LARGE -> {
            return Pair(first = 480, second = 640)
        }
        screenSize >= Configuration.SCREENLAYOUT_SIZE_NORMAL -> {
            return Pair(first = 320, second = 470)
        }
        screenSize >= Configuration.SCREENLAYOUT_SIZE_SMALL -> {
            return Pair(first = 320, second = 426)
        }
    }
    return Pair(0, 0)
}