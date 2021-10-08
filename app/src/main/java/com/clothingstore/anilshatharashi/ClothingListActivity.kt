package com.clothingstore.anilshatharashi

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.clothingstore.anilshatharashi.databinding.ActivityClothingListBinding
import com.clothingstore.anilshatharashi.presentation.ClothingListViewModel
import com.clothingstore.anilshatharashi.presentation.ui.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClothingListActivity : AppCompatActivity() {

    private val clothingListViewModel: ClothingListViewModel by viewModels()
    private lateinit var binding: ActivityClothingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            addFragment(ClothingListFragment.newInstance(getSmallestWidthOfTheDevice()), null)
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

}