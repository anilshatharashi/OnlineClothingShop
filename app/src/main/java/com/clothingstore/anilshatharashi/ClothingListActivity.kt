package com.clothingstore.anilshatharashi

import android.content.res.Configuration
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.clothingstore.anilshatharashi.databinding.ActivityClothingListBinding
import com.clothingstore.anilshatharashi.presentation.ClothingListViewModel
import com.clothingstore.anilshatharashi.presentation.ui.ClothingDetailFragment
import com.clothingstore.anilshatharashi.presentation.ui.ClothingListFragment
import com.clothingstore.anilshatharashi.presentation.ui.addFragment
import com.clothingstore.anilshatharashi.presentation.ui.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsets

import android.view.WindowMetrics


@AndroidEntryPoint
class ClothingListActivity : AppCompatActivity() {

    private val clothingListViewModel: ClothingListViewModel by viewModels()
    private lateinit var binding: ActivityClothingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dimens = determineScreenSize()

        if (savedInstanceState == null) {
            addFragment(ClothingListFragment.newInstance(dimens.first, dimens.second), null)
        }
        clothingListViewModel.selectedClothingLiveData.observe(this, {
            if (supportFragmentManager.findFragmentByTag(ClothingDetailFragment.FRAGMENT_TAG) == null) {
                replaceFragment(
                    ClothingDetailFragment.newInstance(it),
                    ClothingDetailFragment.FRAGMENT_TAG
                )
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

     private fun determineScreenSize(): Pair<Int, Int> {
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
    private fun determineScreenSize2(): Pair<Int, Int> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            // Gets all excluding insets
            // Gets all excluding insets
            val windowInsets = metrics.windowInsets
            val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()
            )
            val insetsWidth: Int = insets.right + insets.left
            val insetsHeight: Int = insets.top + insets.bottom
            val bounds = metrics.bounds
            val first = bounds.width() - insetsWidth
            val second = bounds.height() - insetsHeight
            return Pair(first, second)
        } else {
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            val scaleFactor = metrics.density

            val widthPixels: Int = metrics.widthPixels
            val heightPixels: Int = metrics.heightPixels
            val widthDp = widthPixels / scaleFactor
            val heightDp = heightPixels / scaleFactor
            Log.i("**", "======== = ")
            return Pair(widthDp.toInt(), heightDp.toInt())
        }
    }
}