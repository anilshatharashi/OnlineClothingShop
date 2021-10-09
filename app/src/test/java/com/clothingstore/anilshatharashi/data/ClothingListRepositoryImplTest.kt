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
    private lateinit var remoteDataSource: ClothingListRemoteDataSource
    private lateinit var repository: ClothingListRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        networkHandler = mockk()
        mapper = ClothingListDomainMapper()
        repository = ClothingListRepositoryImpl(remoteDataSource, mapper)
    }

    @Test
    fun `getClothingListList calls a correct function`() = runBlocking {
        val clothingListFlow = MutableSharedFlow<ClothingListResponse>()
        coEvery { networkHandler.isConnected() } returns true
        coEvery { remoteDataSource.fetchClothingListData(1) } returns clothingListFlow

        repository.getClothingList(1)

        coVerify(exactly = 1) { remoteDataSource.fetchClothingListData(1) }
    }

    @Test
    fun `throw NoInternet Exception when it's not connected to internet`() {
        val clothingListFlow = MutableSharedFlow<ClothingListResponse>()

        coEvery { networkHandler.isConnected() } returns false
        coEvery { remoteDataSource.fetchClothingListData(1) } returns clothingListFlow

        runBlocking {
            try {
                repository.getClothingList(1)
            } catch (e: Exception) {
                assertTrue(e is NoInternet)
            }
        }
    }

    @Test
    fun `test exception thrown from the network call should not be caught inside repository`() {
        coEvery { networkHandler.isConnected() } returns true
        coEvery { remoteDataSource.fetchClothingListData(1) } throws UnknownHostException()
        runBlocking {
            try {
                repository.getClothingList(1)
            } catch (e: Exception) {
                assertTrue(e is UnknownHostException)
            }
        }
    }
}