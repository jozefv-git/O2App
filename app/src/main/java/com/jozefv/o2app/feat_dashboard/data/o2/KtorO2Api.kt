package com.jozefv.o2app.feat_dashboard.data.o2

import com.jozefv.o2app.core.data.ktorHttpNetworkResponse
import com.jozefv.o2app.core.data.o2.O2ApiDefaults.QUERY_CODE_PARAM
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import com.jozefv.o2app.core.domain.util.ErrorResult
import com.jozefv.o2app.core.domain.util.ResultHandler
import com.jozefv.o2app.feat_dashboard.data.o2.dto.O2Dto
import com.jozefv.o2app.feat_dashboard.data.o2.dto.mapper.toO2
import com.jozefv.o2app.feat_dashboard.domain.util.O2CodeAlias
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2Api
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class Ktor02Api(
    private val httpClient: HttpClient,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : O2Api {
    override suspend fun activateCode(code: O2CodeAlias): ResultHandler<O2, ErrorResult.NetworkError> {
        return when (val result =
            ktorHttpNetworkResponse<O2Dto>(
                coroutineDispatcherProvider = coroutineDispatcherProvider,
                request = {
                    httpClient.get {
                        parameter(QUERY_CODE_PARAM, code)
                    }
                }
            )
        ) {
            is ResultHandler.Error -> ResultHandler.Error(result.error)
            is ResultHandler.Success -> {
                try {
                    ResultHandler.Success(result.data.toO2())
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    ResultHandler.Error(ErrorResult.NetworkError.SERIALIZATION)
                }
            }
        }
    }
}