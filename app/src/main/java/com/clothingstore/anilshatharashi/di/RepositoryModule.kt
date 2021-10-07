package com.clothingstore.anilshatharashi.di

import com.clothingstore.anilshatharashi.data.mapper.ClothingListDomainMapper
import com.clothingstore.anilshatharashi.data.repository.ClothingListRepositoryImpl
import com.clothingstore.anilshatharashi.data.repository.remote.ClothingListRemoteDataSource
import com.clothingstore.anilshatharashi.data.repository.remote.model.ClothingListResponse
import com.clothingstore.anilshatharashi.domain.ClothingList
import com.clothingstore.anilshatharashi.domain.ClothingListRepository
import com.clothingstore.anilshatharashi.domain.mapper.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideClothingListDomainMapper(): Mapper<ClothingListResponse?, ClothingList?> =
        ClothingListDomainMapper()

    @Singleton
    @Provides
    fun provideClothingListRepository(
        remoteDataSource: ClothingListRemoteDataSource,
        mapper: Mapper<ClothingListResponse?, ClothingList?>
    ): ClothingListRepository = ClothingListRepositoryImpl(remoteDataSource, mapper)

}