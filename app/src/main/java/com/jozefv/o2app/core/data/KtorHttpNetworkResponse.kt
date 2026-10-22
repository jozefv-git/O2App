package com.jozefv.o2app.core.data

import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import com.jozefv.o2app.core.domain.util.ErrorResult
import com.jozefv.o2app.core.domain.util.ResultHandler
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.withContext
import java.nio.channels.UnresolvedAddressException

suspend inline fun <reified T> ktorHttpNetworkResponse(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    request: () -> HttpResponse
): ResultHandler<T, ErrorResult.NetworkError> {
    val response = try {
        request()
    } catch (e: Exception) {
        return when (e) {
            is UnresolvedAddressException -> {
                ResultHandler.Error(ErrorResult.NetworkError.NO_INTERNET)
            }

            else -> {
                ResultHandler.Error(ErrorResult.NetworkError.OTHER)
            }
        }
    }
    // Check errors returned from the server - after request was successful
    return when (response.status.value) {
        in 200..299 -> {
            try {
                withContext(coroutineDispatcherProvider.io) {
                    ResultHandler.Success(response.body<T>())
                }
            } catch (e: NoTransformationFoundException) {
                // Unable to parse with kotlinx serialization - to specified data class
                ResultHandler.Error(ErrorResult.NetworkError.SERIALIZATION)
            }
        }

        401 -> ResultHandler.Error(ErrorResult.NetworkError.UNAUTHORIZED)
        404 -> ResultHandler.Error(ErrorResult.NetworkError.NO_INTERNET)
        429 -> ResultHandler.Error(ErrorResult.NetworkError.TOO_MANY_REQUESTS)
        500 -> ResultHandler.Error(ErrorResult.NetworkError.INTERNAL_ERROR)
        else -> ResultHandler.Error(ErrorResult.NetworkError.OTHER)
    }
}