package com.clothingstore.anilshatharashi.domain

import com.clothingstore.anilshatharashi.domain.usecase.GetClothingListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetClothingListUseCaseTest {
    private lateinit var useCase: GetClothingListUseCase
    private lateinit var repository: ClothingListRepository
    private lateinit var sortBy: SortBy
    private val clothingList = flowOf(
        ClothingList(
            listOf(
                Clothing(id = "2", "Frankfurt Clothing1", openState = OpenState.OPEN),
                Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                Clothing(id = "52", name = "Berlin Res1", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "55", name = "Berlin Res2", openState = OpenState.CLOSED),
                Clothing(id = "42", "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "62", name = "Berlin Res3", openState = OpenState.ORDER_AHEAD),
            )
        )
    )

    private val clothingList2 = flowOf(
        ClothingList(
            listOf(
                Clothing(
                    "2", "Frankfurt Clothing1", OpenState.OPEN,
                    SortingValues(
                        123.0, 44.0, 4.9, 1111, 1.0, 22, minCost = 3
                    )
                ),
                Clothing(
                    "82", "Indian Clothing", OpenState.CLOSED,
                    SortingValues(
                        1234.0, 55.0, 4.8, 2222, 2.9, 33, minCost = 2
                    )
                ),
                Clothing(
                    "52", "Berlin Res1", OpenState.ORDER_AHEAD,
                    SortingValues(
                        1235.0, 66.0, 3.8, 3333, 3.9, 44, minCost = 4
                    )
                ),
                Clothing(
                    "55", "Berlin Res2", OpenState.CLOSED,
                    SortingValues(
                        1236.0, 77.0, 5.0, 4444, 4.9, 55, minCost = 435
                    )
                ),
                Clothing(
                    "42", "Frankfurt Clothing", OpenState.OPEN,
                    SortingValues(
                        1237.0, 88.0, 1.0, 5555, 5.9, 66, minCost = 6
                    )
                ),
                Clothing(
                    "62", "Berlin Res3", OpenState.ORDER_AHEAD,
                    SortingValues(
                        1238.0, 99.0, 2.5, 6666, 6.9, 77, minCost = 7
                    )
                ),
            )
        )
    )

    @Before
    fun setUp() {
        repository = mockk()
        sortBy = SortBy()
        useCase = GetClothingListUseCase(repository)
    }

    @Test
    fun `test execute calls the repository method only once`() {
        val flow = mockk<Flow<ClothingList>>()
        coEvery { repository.getClothingList() } returns flow

        runBlocking { useCase.execute(sortBy) }

        coVerify(exactly = 1) { repository.getClothingList() }
    }

    @Test
    fun `should return the list with the default sorting`() {
        val firstList = flowOf(
            ClothingList(
                listOf(
                    Clothing(id = "2", "Frankfurt Clothing1", openState = OpenState.OPEN),
                    Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                    Clothing(id = "42", "Frankfurt Clothing", openState = OpenState.OPEN),
                    Clothing(id = "52", name = "Berlin Res1", openState = OpenState.ORDER_AHEAD),
                    Clothing(id = "55", name = "Berlin Res2", openState = OpenState.CLOSED),
                    Clothing(id = "62", name = "Berlin Res3", openState = OpenState.ORDER_AHEAD),
                )
            )
        )

        coEvery { repository.getClothingList() } returns firstList

        // WHEN I filter
        val sortedResult =
            listOf(
                Clothing(id = "2", name = "Frankfurt Clothing1", openState = OpenState.OPEN),
                Clothing(id = "42", name = "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "52", name = "Berlin Res1", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "62", name = "Berlin Res3", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                Clothing(id = "55", name = "Berlin Res2", openState = OpenState.CLOSED),
            )
        runBlocking {
            val result = useCase.execute(sortBy)
            assertEquals(sortedResult, result.first()!!)
        }
    }

    @Test
    fun `orderBy Open should work as expected`() {
        coEvery { repository.getClothingList() } returns clothingList
        val sortedByOpen =
            listOf(
                Clothing(id = "2", name = "Frankfurt Clothing1", openState = OpenState.OPEN),
                Clothing(id = "42", name = "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "52", name = "Berlin Res1", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "62", name = "Berlin Res3", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                Clothing(id = "55", name = "Berlin Res2", openState = OpenState.CLOSED),
            )

        runBlocking {
            // SortedBy OPEN
            sortBy = SortBy(openState = OpenState.OPEN)
            val openResult = useCase.execute(sortBy)
            assertEquals(sortedByOpen, openResult.first()!!)
        }
    }

    @Test
    fun `orderBy OrderAhead should work as expected`() {
        coEvery { repository.getClothingList() } returns clothingList

        val sortedByOrderAhead =
            listOf(
                Clothing(id = "52", name = "Berlin Res1", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "62", name = "Berlin Res3", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "2", name = "Frankfurt Clothing1", openState = OpenState.OPEN),
                Clothing(id = "42", name = "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                Clothing(id = "55", name = "Berlin Res2", openState = OpenState.CLOSED),
            )

        runBlocking {
            // SortedBy ORDER_AHEAD
            sortBy = SortBy(openState = OpenState.ORDER_AHEAD)
            val orderAheadResult = useCase.execute(sortBy)
            assertEquals(sortedByOrderAhead, orderAheadResult.first()!!)
        }
    }

    @Test
    fun `orderBy Closed should work as expected`() {
        coEvery { repository.getClothingList() } returns clothingList

        val sortedByClosed =
            listOf(
                Clothing(id = "82", name = "Indian Clothing", openState = OpenState.CLOSED),
                Clothing(id = "55", name = "Berlin Res2", openState = OpenState.CLOSED),
                Clothing(id = "2", name = "Frankfurt Clothing1", openState = OpenState.OPEN),
                Clothing(id = "42", name = "Frankfurt Clothing", openState = OpenState.OPEN),
                Clothing(id = "52", name = "Berlin Res1", openState = OpenState.ORDER_AHEAD),
                Clothing(id = "62", name = "Berlin Res3", openState = OpenState.ORDER_AHEAD),
            )

        runBlocking {
            // SortedBy OPEN
            sortBy = SortBy(openState = OpenState.CLOSED)
            val openResult = useCase.execute(sortBy)
            assertEquals(sortedByClosed, openResult.first()!!)
        }
    }

    @Test
    fun `test orderBy Closed with MinCost Filter`() = runBlocking {
        coEvery { repository.getClothingList() } returns clothingList2

        val sortedByMinCost =
            listOf(
                Clothing(
                    "2", "Frankfurt Clothing1", OpenState.OPEN,
                    SortingValues(
                        123.0, 44.0, 4.9, 1111, 1.0, 22, minCost = 3
                    )
                ),
                Clothing(
                    "42", "Frankfurt Clothing", OpenState.OPEN,
                    SortingValues(
                        1237.0, 88.0, 1.0, 5555, 5.9, 66, minCost = 6
                    )
                ),
                Clothing(
                    "52", "Berlin Res1", OpenState.ORDER_AHEAD,
                    SortingValues(
                        1235.0, 66.0, 3.8, 3333, 3.9, 44, minCost = 4
                    )
                ),
                Clothing(
                    "62", "Berlin Res3", OpenState.ORDER_AHEAD,
                    SortingValues(
                        1238.0, 99.0, 2.5, 6666, 6.9, 77, minCost = 7
                    )
                ),
                Clothing(
                    "82", "Indian Clothing", OpenState.CLOSED,
                    SortingValues(
                        1234.0, 55.0, 4.8, 2222, 2.9, 33, minCost = 2
                    )
                ),
                Clothing(
                    "55", "Berlin Res2", OpenState.CLOSED,
                    SortingValues(
                        1236.0, 77.0, 5.0, 4444, 4.9, 55, minCost = 435
                    )
                ),
            )

        // SortedBy OPEN
        sortBy = SortBy(filterBy = FilterBy.MIN_COST)
        val openResult = useCase.execute(sortBy)
        assertEquals(sortedByMinCost, openResult.first()!!)
    }

    @Test
    fun `test filterBy ClothingName with Distance Filter`() = runBlocking {
        coEvery { repository.getClothingList() } returns clothingList2

        val sortedByDistance =
            listOf(
                Clothing(
                    "52", "Berlin Res1", OpenState.ORDER_AHEAD,
                    SortingValues(
                        1235.0, 66.0, 3.8, 3333, 3.9, 44, minCost = 4
                    )
                ),
                Clothing(
                    "62", "Berlin Res3", OpenState.ORDER_AHEAD,
                    SortingValues(
                        1238.0, 99.0, 2.5, 6666, 6.9, 77, minCost = 7
                    )
                ),
                Clothing(
                    "55", "Berlin Res2", OpenState.CLOSED,
                    SortingValues(
                        1236.0, 77.0, 5.0, 4444, 4.9, 55, minCost = 435
                    )
                ),
            )

        // SortedBy OPEN
        sortBy = SortBy(name = "berl", filterBy = FilterBy.DISTANCE)
        val openResult = useCase.execute(sortBy)
        assertEquals(sortedByDistance, openResult.first()!!)
    }
}