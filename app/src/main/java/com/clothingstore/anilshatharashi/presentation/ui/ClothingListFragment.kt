package com.clothingstore.anilshatharashi.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clothingstore.anilshatharashi.R
import com.clothingstore.anilshatharashi.databinding.FragmentClothingListBinding
import com.clothingstore.anilshatharashi.presentation.ClothingListState.*
import com.clothingstore.anilshatharashi.presentation.ClothingListViewModel
import com.clothingstore.anilshatharashi.presentation.ErrorFetchingClothingListData
import com.clothingstore.anilshatharashi.presentation.NoInternet
import com.clothingstore.anilshatharashi.presentation.UnknownException
import com.clothingstore.anilshatharashi.presentation.model.UiClothingModel
import com.clothingstore.anilshatharashi.presentation.ui.RecyclerViewPaginationListener.Companion.PAGE_START
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClothingListFragment : BaseFragment() {

    private var _binding: FragmentClothingListBinding? = null
    private val binding get() = _binding!!

    private lateinit var clothingListAdapter: ClothingListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var _progressBar: ProgressBar
    private lateinit var emptyStateView: TextView
    private val viewModel: ClothingListViewModel by activityViewModels()

    private var pageIndex: Int = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.smallestWidth = arguments?.getInt(SMALLEST_WIDTH) ?:0
        Log.i("**", "Width = ${viewModel.smallestWidth}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothingListBinding.inflate(inflater, container, false)
        binding.apply {
            recyclerView = clothingListRecyclerView
            val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

            recyclerView.layoutManager = layoutManager
            _progressBar = progressBar
            emptyStateView = emptyState

            recyclerView.addOnScrollListener(object :
                RecyclerViewPaginationListener(layoutManager) {

                override val isLastPage: Boolean
                    get() = viewModel.isLastPage.value?:false

                override val isLoading: Boolean
                    get() = viewModel.isNextPageLoading.value?:false

                override fun loadMoreItems() {
                    viewModel.pageIndex.value = pageIndex++
                    viewModel.fetchClothingList()
                }
            })

        }
        clothingListAdapter = ClothingListAdapter { viewModel.onClothingItemSelected(it) }
        recyclerView.adapter = clothingListAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateToolbar(getString(R.string.title_clothing_list_fragment))
        viewModel.clothingListState.observe(viewLifecycleOwner) {
            when (it) {
                is Loading -> showLoadingView()
                is Success -> showContentView(it.uiModel)
                is Failure -> handleFailure(it.exception)
                else -> return@observe
            }
        }
        viewModel.isNextPageLoading.observe(viewLifecycleOwner) {
            if (it == true) clothingListAdapter.addProgressBar()
        }
    }

    fun showLoadingView() {
        _progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    fun hideLoadingView() {
        _progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    fun handleFailure(exception: Exception) {
        when (exception) {
            is NoInternet -> showErrorMessage(getString(R.string.no_internet_message))
            is ErrorFetchingClothingListData -> showErrorMessage(getString(R.string.error_message))
            is UnknownException -> showErrorMessage(getString(R.string.unknown_error_message))
        }
    }

    fun showErrorMessage(message: String) {
        recyclerView.visibility = View.GONE
        _progressBar.visibility = View.GONE
        emptyStateView.visibility = View.VISIBLE
        emptyStateView.text = message
    }

    fun showContentView(uiModel: UiClothingModel) {
        if (pageIndex != PAGE_START) clothingListAdapter.removeProgressBar()
        clothingListAdapter.addClothingList(uiModel.clothingList)

        hideLoadingView()
        emptyStateView.visibility = View.GONE
    }

    private fun updateToolbar(toolbarTitle: String?) {
        val clothingActivity = activity as AppCompatActivity
        clothingActivity.supportActionBar?.apply {
            title = toolbarTitle
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowTitleEnabled(true)
        }
    }

    override fun onContentError() {
        showErrorMessage(getString(R.string.no_internet_message))
    }

    override fun onContentAvailable() {
        if (emptyStateView.visibility != View.VISIBLE) return
        viewModel.fetchClothingList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SMALLEST_WIDTH = "width"

        fun newInstance(width: Int) = ClothingListFragment().apply {
            arguments = bundleOf(SMALLEST_WIDTH to width)
        }
    }
}
