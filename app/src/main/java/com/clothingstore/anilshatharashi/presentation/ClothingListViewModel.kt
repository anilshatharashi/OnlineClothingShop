package com.clothingstore.anilshatharashi.presentation

import android.util.Log
import androidx.lifecycle.*
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.usecase.GetClothingListUseCase
import com.clothingstore.anilshatharashi.presentation.ClothingListState.*
import com.clothingstore.anilshatharashi.presentation.mapper.ClothingListUiMapper
import com.clothingstore.anilshatharashi.presentation.model.UiClothing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothingListViewModel @Inject constructor(
    private val getClothingListUseCase: GetClothingListUseCase,
    private val mapper: ClothingListUiMapper,
) : ViewModel() {
    val pageIndex = MutableLiveData(1)
    var smallestWidth: Int = 0

    private val _isLastPage = MutableLiveData(false)
    val isLastPage: LiveData<Boolean> = _isLastPage

    private val _isNextPageLoading = MutableLiveData(false)
    val isNextPageLoading: LiveData<Boolean> = _isNextPageLoading

    private val _selectedClothingLiveData = SingleLiveEvent<UiClothing>()
    val selectedClothingLiveData: LiveData<UiClothing> = _selectedClothingLiveData

    private val _clothingListState = MutableStateFlow<ClothingListState>(Loading)
    val clothingListState: LiveData<ClothingListState> = _clothingListState.asLiveData()

    init {
        fetchClothingList()
    }

    fun fetchClothingList() {
        val currentPage = pageIndex.value?:1
        if (currentPage > 1) _isNextPageLoading.postValue(true)
        viewModelScope.launch {
            try {
                getClothingListUseCase.execute(currentPage)
                    .collect {
                        _clothingListState.value = it?.let { handleSuccess(it) }
                            ?: Failure(ErrorFetchingClothingListData)
                    }
            } catch (exception: Exception) {
                Log.e("***ViewModel", " stacktrace =${exception.printStackTrace()}")
                _clothingListState.value = Failure(exception)
            }
        }
    }

    private fun handleSuccess(it: ClothingList): Success {
        mapper.smallestWidth = smallestWidth
        _isLastPage.postValue(it.meta?.end)
        _isNextPageLoading.postValue(false)
        return Success(mapper.mapFrom(it))
    }

    fun onClothingItemSelected(uiClothing: UiClothing) {
        _selectedClothingLiveData.postValue(uiClothing)
    }
}
