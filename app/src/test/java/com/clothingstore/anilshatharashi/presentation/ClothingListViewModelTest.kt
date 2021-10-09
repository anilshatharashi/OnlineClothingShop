package com.clothingstore.anilshatharashi.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.clothingstore.anilshatharashi.domain.Clothing
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.usecase.GetClothingListUseCase
import com.clothingstore.anilshatharashi.presentation.ClothingListState.Success
import com.clothingstore.anilshatharashi.presentation.mapper.ClothingListUiMapper
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatter
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatterImpl
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ClothingListViewModelTest {

    private lateinit var clothingListFlow: MutableSharedFlow<ClothingList?>
    private lateinit var clothingListViewModel: ClothingListViewModel
    private lateinit var getClothingListUseCase: GetClothingListUseCase
    private lateinit var dateFormatter: DateFormatter
    private lateinit var uiMapper: ClothingListUiMapper

    @get:Rule
    val taskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        getClothingListUseCase = mockk()
        clothingListFlow = MutableSharedFlow()

        dateFormatter = DateFormatterImpl(mockk())
        uiMapper = ClothingListUiMapper(dateFormatter)
        coEvery { getClothingListUseCase.execute(1) } returns clothingListFlow

        clothingListViewModel = ClothingListViewModel(getClothingListUseCase, uiMapper)
    }

    @Test
    fun `page should be error page if null is returned`() {

        observeState {
            clothingListViewModel.fetchClothingList()
            runBlocking { clothingListFlow.emit(null) }
            verify { it.onChanged(ClothingListState.Failure(ErrorFetchingClothingListData)) }
        }
    }

    @Test
    fun `getClothingListData should push clothingLists to the ui`() {
        // GIVEN I select a clothingList id

        observeState {
            clothingListViewModel.fetchClothingList()

            // WHEN I clothingList is emitted
            val clothingList = ClothingList(listOf(Clothing(id = 82),
                Clothing(id = 42),
                Clothing(id = 52)))
            runBlocking { clothingListFlow.emit(clothingList) }

            // THEN it should be observed
            verify(exactly = 1) {
                it.onChanged(Success(uiMapper.mapFrom(clothingList)))
            }
        }
    }

    @Test
    fun `getClothingListData should only emit clothingLists for the last requested id`() {
        // GIVEN there are two clothingList ids
        val firstPageFlow = MutableSharedFlow<ClothingList>()
        val secondPageFlow = MutableSharedFlow<ClothingList>()

        coEvery { getClothingListUseCase.execute(1) } returns firstPageFlow
        coEvery { getClothingListUseCase.execute(1) } returns secondPageFlow

        observeState {
            // WHEN I subscribe first to one, then to the second
            clothingListViewModel.fetchClothingList()
            clothingListViewModel.fetchClothingList()

            // AND there is a clothingList for both ids
            val list1 = ClothingList(
                clothingList = listOf(Clothing(id = 82), Clothing(id = 42), Clothing(id = 52))
            )
            val list2 = ClothingList(
                clothingList = listOf(Clothing(id = 382), Clothing(id = 242), Clothing(id = 2542))
            )
            runBlocking {
                firstPageFlow.emit(list1)
                secondPageFlow.emit(list2)
            }

            // THEN the ui should only get the clothingList from the last subscribed id
            verify(exactly = 1) {
                it.onChanged(Success(uiMapper.mapFrom(list2)))
            }
            verify(exactly = 0) {
                it.onChanged(Success(uiMapper.mapFrom(list1)))
            }
        }
    }

    private fun observeState(block: (Observer<ClothingListState>) -> Unit) {
        val observer = mockk<Observer<ClothingListState>>(relaxUnitFun = true)
        clothingListViewModel.clothingListState.observeForever(observer)
        try {
            block(observer)
        } finally {
            clothingListViewModel.clothingListState.removeObserver(observer)
        }
    }
}