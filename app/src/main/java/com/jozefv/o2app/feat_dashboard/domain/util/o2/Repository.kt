package com.jozefv.o2app.feat_dashboard.domain.util.o2

import com.jozefv.o2app.core.domain.util.ErrorResult
import com.jozefv.o2app.core.domain.util.ResultHandler
import com.jozefv.o2app.feat_dashboard.domain.util.O2CodeAlias
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun generateCode(): O2CodeAlias
    suspend fun saveCode(o2CodeAlias: O2CodeAlias)
    fun observeCode(): Flow<O2Code?>
    suspend fun activateCode(o2CodeAlias: O2CodeAlias): ResultHandler<O2Code, ErrorResult.NetworkError>
}