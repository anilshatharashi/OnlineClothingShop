package com.clothingstore.anilshatharashi.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.clothingstore.anilshatharashi.R
import com.clothingstore.anilshatharashi.ClothingListActivity
import com.clothingstore.anilshatharashi.presentation.model.UiClothing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClothingDetailFragment : Fragment() {

    private var clothingId: String? = null
    private var clothing: UiClothing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            clothingId = arguments?.getString(SELECTED_RESTAURANT_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_clothing_detail, container, false)
        clothing?.let { renderDataOnUi(rootView, it) }
        return rootView
    }

    private fun renderDataOnUi(rootView: View?, model: UiClothing) {
       /* updateToolbar(model.name)
        rootView?.run {
            detailTitleView.text = model.name
            detailReleasedOnView.text = getString(R.string.published_date, model.openState)

            userRatingsValueView.text = model.uiSortingValues.ratingAverage.toString()

        }*/
    }

    private fun updateToolbar(toolbarTitle: String?) {
        val clothinglistActivity = activity as ClothingListActivity
        clothinglistActivity.supportActionBar?.apply {
            title = toolbarTitle
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
    }

    companion object {
        const val FRAGMENT_TAG = "clothing_details_frag"
        private const val SELECTED_RESTAURANT_KEY = "selected_clothing"
        fun newInstance(clothingId: Int): ClothingDetailFragment =
            ClothingDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(SELECTED_RESTAURANT_KEY, clothingId)
                }
            }
    }

}
