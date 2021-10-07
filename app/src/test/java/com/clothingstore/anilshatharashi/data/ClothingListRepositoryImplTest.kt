package com.clothingstore.anilshatharashi.data

import com.clothingstore.anilshatharashi.data.mapper.ClothingListDomainMapper
import com.clothingstore.anilshatharashi.data.repository.ClothingListRepositoryImpl
import com.clothingstore.anilshatharashi.data.repository.remote.ClothingListRemoteDataSource
import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.mapper.Mapper
import com.clothingstore.anilshatharashi.platform.NetworkHandler
import com.clothingstore.anilshatharashi.presentation.NoInternet
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class ClothingListRepositoryImplTest {

    private lateinit var networkHandler: NetworkHandler
    private lateinit var mapper: Mapper<ClothingListResponse?, ClothingList?>
    private lateinit var clothinglistRemoteDataSource: ClothingListRemoteDataSource
    private lateinit var repository: ClothingListRepositoryImpl

    @Before
    fun setUp() {
        clothinglistRemoteDataSource = mockk()
        networkHandler = mockk()
        mapper = ClothingListDomainMapper()
        repository = ClothingListRepositoryImpl(clothinglistRemoteDataSource, mapper)
    }

    @Test
    fun `getClothingListList calls a correct function`() = runBlocking {
        val clothinglistFlow = MutableSharedFlow<ClothingListResponse>()
        coEvery { networkHandler.isConnected() } returns true
        coEvery { clothinglistRemoteDataSource.fetchClothingListData() } returns clothinglistFlow

        repository.getClothingList()

        coVerify(exactly = 1) { clothinglistRemoteDataSource.fetchClothingListData() }
    }

    @Test
    fun `throw NoInternet Exception when it's not connected to internet`() {
        val clothinglistFlow = MutableSharedFlow<ClothingListResponse>()

        coEvery { networkHandler.isConnected() } returns false
        coEvery { clothinglistRemoteDataSource.fetchClothingListData() } returns clothinglistFlow

        runBlocking {
            try {
                repository.getClothingList()
            } catch (e: Exception) {
                assertTrue(e is NoInternet)
            }
        }
    }

    @Test
    fun `test exception thrown from the network call should not be caught inside repository`() {
        coEvery { networkHandler.isConnected() } returns true
        coEvery { clothinglistRemoteDataSource.fetchClothingListData() } throws UnknownHostException()
        runBlocking {
            try {
                repository.getClothingList()
            } catch (e: Exception) {
                assertTrue(e is UnknownHostException)
            }
        }
    }
}