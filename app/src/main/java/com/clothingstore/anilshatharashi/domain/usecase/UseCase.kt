package com.clothingstore.anilshatharashi.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

abstract class UseCase<RESULT> {

    suspend fun execute(): Flow<RESULT> =
        withContext(Dispatchers.IO) { buildUseCase() }

    abstract suspend fun buildUseCase(): Flow<RESULT>

}

