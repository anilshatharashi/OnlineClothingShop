package com.clothingstore.anilshatharashi.presentation.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clothingstore.anilshatharashi.R
import com.clothingstore.anilshatharashi.databinding.ViewItemClothingListBinding
import com.clothingstore.anilshatharashi.databinding.ViewItemLoadingBinding
import com.clothingstore.anilshatharashi.presentation.model.UiClothing

class ClothingListAdapter(private val clothingItemClickListener: (id: Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var clothingItemBinding: ViewItemClothingListBinding
    private lateinit var loadingItemBinding: ViewItemLoadingBinding
    private val clothingList = mutableListOf<UiClothing?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOADING -> {
                loadingItemBinding = ViewItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                LoadingViewHolder(loadingItemBinding)
            }
            else -> {
                clothingItemBinding = ViewItemClothingListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                ClothingItemViewHolder(clothingItemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ClothingItemViewHolder ->
                clothingList[position]?.run {
                    holder.bind(this)
                    holder.itemView.setOnClickListener { clothingItemClickListener.invoke(id) }
                }
        }
    }

    override fun getItemCount(): Int = clothingList.size

    override fun getItemViewType(position: Int): Int =
        if (clothingList[position] == null) TYPE_LOADING else TYPE_CONTENT

    fun addProgressBar() {
        clothingList.add(null)
        notifyItemInserted(clothingList.size - 1)
    }

    fun removeProgressBar() {
        clothingList.apply {
            if (size != 0) {
                removeAt(size - 1)
                notifyItemRemoved(size)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addClothingList(list: List<UiClothing>) {
        clothingList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ClothingItemViewHolder(private val binding: ViewItemClothingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clothingModel: UiClothing) {
            binding.activeStatusView.text = clothingModel.activeStatus
            clothingModel.picturesData.let {
                it.first().thumbnailAndCoverPhotoUrls.first
                    ?.let { url -> binding.thumbnailView.loadFromUrl(url) }
            }
            binding.addressView.text = clothingModel.address
            binding.descriptionView.text = clothingModel.description
            binding.priceAmountView.text = binding.root.context?.getString(
                R.string.price_amount,
                clothingModel.priceAmount
            )
            binding.publishedDateView.text = binding.root.context?.getString(
                R.string.published_date,
                clothingModel.publishedDate
            )
        }

    }

    inner class LoadingViewHolder(binding: ViewItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemProgressBar
        }
    }

    companion object {
        private const val TYPE_LOADING = 0
        private const val TYPE_CONTENT = 1

        // Hardcoding the posterSize as I am not maintaining the configuration
        // in local Database. Ideal way is to make a network request to fetch the Configuration and keep updating it
        // (for every two weeks or four weeks)
        private const val posterSize: String = "/w300/"
    }
}