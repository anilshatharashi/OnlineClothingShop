package com.clothingstore.anilshatharashi.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.clothingstore.anilshatharashi.domain.*
import com.clothingstore.anilshatharashi.domain.usecase.GetClothingListUseCase
import com.clothingstore.anilshatharashi.presentation.ClothingListState.Success
import com.clothingstore.anilshatharashi.presentation.mapper.ClothingListUiMapper
import com.clothingstore.anilshatharashi.presentation.model.UiFilterBy
import com.clothingstore.anilshatharashi.presentation.model.UiOpenStates
import com.clothingstore.anilshatharashi.presentation.model.UiSortBy
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ClothingListViewModelTest {

    private lateinit var clothingListViewModel: ClothingListViewModel
    private lateinit var getClothingListUseCase: GetClothingListUseCase
    private lateinit var uiMapper: ClothingListUiMapper
    private lateinit var uiSortBy: UiSortBy

    @get:Rule
    val taskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        uiSortBy = UiSortBy(name = "", openState = UiOpenStates.OPEN, filterBy = UiFilterBy.NONE)
        getClothingListUseCase = mockk()
        uiMapper = ClothingListUiMapper()
        clothingListViewModel = ClothingListViewModel(getClothingListUseCase, uiMapper)
    }

    @Test
    fun `page should be error page if null is returned`() {
        val clothinglistFlow = MutableSharedFlow<List<Clothing>?>()

        observeState {
            coEvery { getClothingListUseCase.execute(uiMapper.mapToFilterBy(uiSortBy)) } returns clothinglistFlow
            clothingListViewModel.fetchClothingList(uiSortBy)
            runBlocking {
                clothinglistFlow.emit(null)
            }
            verify { it.onChanged(ClothingListState.Failure(ErrorFetchingClothingListData)) }
        }
    }

    @Test
    fun `getClothingListData should push clothinglist to the ui`() {
        // GIVEN I select a clothinglist id
        val clothinglistFlow = MutableSharedFlow<List<Clothing>?>()

        val uiSortBy = UiSortBy(name = "", UiOpenStates.OPEN, filterBy = UiFilterBy.NONE)
        coEvery { getClothingListUseCase.execute(uiMapper.mapToFilterBy(uiSortBy)) } returns clothinglistFlow
        observeState {
            clothingListViewModel.fetchClothingList(uiSortBy)

            // WHEN I push a clothinglist
            val clothinglist = listOf(
                Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                Clothing(id = "42", name = "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "52", name = "Berlin Res", openState = OpenState.ORDER_AHEAD),
            )
            runBlocking { clothinglistFlow.emit(clothinglist) }

            // THEN it should be pushed to the ui
            verify(exactly = 1) { it.onChanged(Success(uiMapper.mapFrom(clothinglist))) }
        }
    }

    @Test
    fun `getClothingListData should only push clothinglist for the last requested id`() {
        // GIVEN there are two clothinglist ids
        val firstPageFlow = MutableSharedFlow<List<Clothing>?>()
        val secondPageFlow = MutableSharedFlow<List<Clothing>?>()

        observeState {
            val uiSortBy = UiSortBy(name = "", UiOpenStates.OPEN, filterBy = UiFilterBy.NONE)
            coEvery { getClothingListUseCase.execute(uiMapper.mapToFilterBy(uiSortBy)) } returns firstPageFlow
            coEvery { getClothingListUseCase.execute(uiMapper.mapToFilterBy(uiSortBy)) } returns secondPageFlow
            // WHEN I subscribe first to one, then to the second
            clothingListViewModel.fetchClothingList(uiSortBy)
            clothingListViewModel.fetchClothingList(uiSortBy)

            // AND there is a clothinglist for both ids
            val list1 = listOf(
                Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                Clothing(id = "42", name = "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "52", name = "Berlin Res", openState = OpenState.ORDER_AHEAD),
            )
            val list2 = listOf(
                Clothing(id = "282", name = "Hyderabad Clothing", openState = OpenState.CLOSED),
                Clothing(id = "432", name = "Hessen Clothing", openState = OpenState.OPEN),
                Clothing(id = "522", name = "Berlin Rest2", openState = OpenState.ORDER_AHEAD),
            )
            runBlocking {
                firstPageFlow.emit(list1)
                secondPageFlow.emit(list2)
            }
            val sortedList1 = listOf(
                Clothing(id = "42", name = "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "52", name = "Berlin Res", openState = OpenState.ORDER_AHEAD),
                Clothing("82", "Indian Clothing", OpenState.CLOSED),
            )
            val sortedList2 = listOf(
                Clothing(id = "432", name = "Hessen Clothing", openState = OpenState.OPEN),
                Clothing(id = "522", name = "Berlin Rest2", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "282", name = "Hyderabad Clothing", openState = OpenState.CLOSED),
            )
            // THEN the ui should only get the clothinglist from the last subscribed id
            verify(exactly = 1) {
                it.onChanged(Success(uiMapper.mapFrom(list2)))
            }
            verify(exactly = 0) { it.onChanged(Success(uiMapper.mapFrom(list1))) }
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