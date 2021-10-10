package com.clothingstore.anilshatharashi.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.clothingstore.anilshatharashi.ClothingListActivity
import com.clothingstore.anilshatharashi.R
import com.clothingstore.anilshatharashi.databinding.FragmentClothingDetailBinding
import com.clothingstore.anilshatharashi.presentation.model.UiClothing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClothingDetailFragment : Fragment() {

    private var clothing: UiClothing? = null

    private var _binding: FragmentClothingDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            clothing = arguments?.getParcelable(SELECTED_CLOTHING_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentClothingDetailBinding.inflate(inflater, container, false)
        val context = binding.root.context
        binding.apply {
            updateToolbar(context?.getString(R.string.clothing_detail))
            detailDescriptionView.text = context?.getString(R.string.description,
                clothing?.description)

            detailsAddressView.text = context?.getString(R.string.address, clothing?.address)
            detailInternationalShippingView.text = context?.getString(
                R.string.international_shipping_cost, clothing?.internationalShippingCost
            )
            detailNationalShippingView.text = context?.getString(R.string.national_shipping_cost,
                clothing?.nationalShippingCost)

            detailPriceView.text = context?.getString(R.string.price_amount, clothing?.priceAmount)
            detailHandDeliveryView.text = context?.getString(R.string.hand_delivery,
                clothing?.handDelivery.toString())

            clothing?.uiPictures?.first()?.thumbnailAndCoverPhotoUrls?.second
                ?.let { url -> detailImageView.loadFromUrl(url) }
        }
        return binding.root
    }

    private fun updateToolbar(toolbarTitle: String?) {
        val clothingListActivity = activity as ClothingListActivity
        clothingListActivity.supportActionBar?.apply {
            title = toolbarTitle
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FRAGMENT_TAG = "clothing_details_frag"
        private const val SELECTED_CLOTHING_KEY = "selected_clothing"
        fun newInstance(uiClothing: UiClothing): ClothingDetailFragment =
            ClothingDetailFragment().apply {
                arguments = bundleOf(SELECTED_CLOTHING_KEY to uiClothing)
            }
    }

}
