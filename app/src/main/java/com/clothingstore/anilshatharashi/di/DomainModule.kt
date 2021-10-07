package com.clothingstore.anilshatharashi.di

import android.content.Context
import android.text.format.DateFormat
import com.clothingstore.anilshatharashi.domain.ClothingListRepository
import com.clothingstore.anilshatharashi.domain.usecase.GetClothingListUseCase
import com.clothingstore.anilshatharashi.presentation.mapper.ClothingListUiMapper
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatter
import com.clothingstore.anilshatharashi.presentation.mapper.parsers.DateFormatterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object DomainModule {

    @ViewModelScoped
    @Provides
    fun provideDateParser(@ApplicationContext context: Context): DateFormatter =
        DateFormatterImpl(DateFormat.getDateFormat(context))

    @ViewModelScoped
    @Provides
    fun provideClothingListUiMapper(dateFormatter: DateFormatter): ClothingListUiMapper =
        ClothingListUiMapper(dateFormatter)

    @Provides
    @ViewModelScoped
    fun provideGetClothingListUseCase(repository: ClothingListRepository): GetClothingListUseCase =
        GetClothingListUseCase(repository)

}