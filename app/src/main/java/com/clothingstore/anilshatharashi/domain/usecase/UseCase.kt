package com.clothingstore.anilshatharashi.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

abstract class UseCase<PARMS, RESULT> {

    suspend fun execute(parms: PARMS): Flow<RESULT> =
        withContext(Dispatchers.IO) { buildUseCase(parms) }

    abstract suspend fun buildUseCase(parms: PARMS): Flow<RESULT>

}

