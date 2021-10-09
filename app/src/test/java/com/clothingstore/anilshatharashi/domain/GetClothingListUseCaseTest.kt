package com.clothingstore.anilshatharashi.domain

import com.clothingstore.anilshatharashi.domain.usecase.GetClothingListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetClothingListUseCaseTest {
    private lateinit var useCase: GetClothingListUseCase
    private lateinit var repository: ClothingListRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetClothingListUseCase(repository)
    }

    @Test
    fun `test execute calls the repository method only once`() {
        val flow = mockk<Flow<ClothingList>>()
        coEvery { repository.getClothingList(any()) } returns flow

        runBlocking { useCase.execute(1) }

        coVerify(exactly = 1) { repository.getClothingList(1) }
    }

    @Test
    fun `test buildUseCase returns the correct data`() {
        val flow = mockk<Flow<ClothingList>>()
        coEvery { repository.getClothingList(any()) } returns flow

        val result = runBlocking { useCase.execute(1) }

        assertEquals(flow, result)
    }
}