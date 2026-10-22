package com.jozefv.o2app.feat_dashboard.data.o2

import com.jozefv.o2app.core.data.o2.O2ApiDefaults.CODE_ACTIVE_STATUS_CODE
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import com.jozefv.o2app.core.domain.util.ErrorResult
import com.jozefv.o2app.core.domain.util.ResultHandler
import com.jozefv.o2app.feat_dashboard.domain.util.O2CodeAlias
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2Api
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2Code
import com.jozefv.o2app.feat_dashboard.domain.util.o2.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.Uuid

class O2Repository(
    private val o2Api: O2Api,
    private val applicationScope: CoroutineScope,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : Repository {
    private val _code = MutableStateFlow<O2Code?>(null)
    private val _generatedCode = MutableStateFlow<O2CodeAlias?>(null)

    override fun observeCode(): Flow<O2Code?> {
        return _code.asStateFlow()
    }

    override suspend fun activateCode(
        o2CodeAlias: O2CodeAlias
    ): ResultHandler<O2Code, ErrorResult.NetworkError> {
        // Launch a new independent coroutine which is independent of the suspending function
        // in our app scope which is not the scope of the outer function.
        // applicationScope lives as long as our app does, so the code won't be canceled if the outer scope cancels.
        return applicationScope.async(coroutineDispatcherProvider.io) {
            when (val result = o2Api.activateCode(code = o2CodeAlias)) {
                is ResultHandler.Error -> ResultHandler.Error(result.error)
                is ResultHandler.Success -> {
                    val isCodeActive = result.data.android > CODE_ACTIVE_STATUS_CODE
                    O2Code(code = o2CodeAlias, isActive = isCodeActive).let { o2Code ->
                        _code.value = o2Code
                        ResultHandler.Success(o2Code)
                    }
                }
            }
        }.await()
    }

    override suspend fun generateCode(): O2CodeAlias {
        // Heavy operation - main safety - switching to the Dispatcher default
        val result = withContext(coroutineDispatcherProvider.default) {
            delay(2.seconds)
            Uuid.random().toString()
        }
        _generatedCode.value = result
        return result
    }

    override suspend fun saveCode(o2CodeAlias: O2CodeAlias) {
        // Heavy operation - main safety - switching to the Dispatcher default
        withContext(coroutineDispatcherProvider.default) {
            delay(2.seconds)
        }
        _code.value = O2Code(
            code = o2CodeAlias,
            isActive = false
        )
    }
}