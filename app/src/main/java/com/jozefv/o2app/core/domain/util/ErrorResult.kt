package com.jozefv.o2app.core.domain.util

sealed interface ErrorResult {
    enum class NetworkError : ErrorResult {
        UNAUTHORIZED,
        TOO_MANY_REQUESTS,
        INTERNAL_ERROR,
        NO_INTERNET,
        SERIALIZATION,
        OTHER
    }
}