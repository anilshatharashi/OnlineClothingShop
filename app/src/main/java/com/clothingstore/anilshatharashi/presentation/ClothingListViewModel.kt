package com.clothingstore.anilshatharashi.presentation

import android.util.Log
import androidx.lifecycle.*
import com.clothingstore.anilshatharashi.domain.usecase.GetClothingListUseCase
import com.clothingstore.anilshatharashi.presentation.ClothingListState.*
import com.clothingstore.anilshatharashi.presentation.mapper.ClothingListUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClothingListViewModel @Inject constructor(
    private val getClothingListUseCase: GetClothingListUseCase,
    private val mapper: ClothingListUiMapper
) : ViewModel() {

    private val _selectedClothingLiveData = SingleLiveEvent<Int>()
    val selectedClothingLiveData: LiveData<Int> = _selectedClothingLiveData

    private val _clothingListState = MutableStateFlow<ClothingListState>(Loading)
    val clothingListState: LiveData<ClothingListState> = _clothingListState.asLiveData()

    init {
        fetchClothingList()
    }

    fun fetchClothingList() {
        viewModelScope.launch {
            try {
                getClothingListUseCase.execute()
                    .collect {
                        _clothingListState.value = it?.let { Success(mapper.mapFrom(it)) }
                            ?: Failure(ErrorFetchingClothingListData)
                    }
            } catch (exception: Exception) {
                Log.e(
                    "***ListViewModel",
                    "exception = $exception\nstacktrace =${exception.printStackTrace()}"
                )
                _clothingListState.value = Failure(exception)
            }
        }
    }

    fun onClothingItemSelected(id: Int) {
        // This feature is not required to be implemented
        _selectedClothingLiveData.postValue(id)
    }
}
