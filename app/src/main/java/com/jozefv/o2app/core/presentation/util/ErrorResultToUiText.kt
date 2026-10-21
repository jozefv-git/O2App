package com.jozefv.o2app.core.presentation.util

import com.jozefv.o2app.R
import com.jozefv.o2app.core.domain.util.ErrorResult

fun ErrorResult.toUiText(): UiText{
    val error = when(this){
        ErrorResult.NetworkError.UNAUTHORIZED -> R.string.error_unauthorized
        ErrorResult.NetworkError.TOO_MANY_REQUESTS->R.string.error_too_many_request
        ErrorResult.NetworkError.INTERNAL_ERROR->R.string.error_internal
        ErrorResult.NetworkError.NO_INTERNET->R.string.error_no_internet
        ErrorResult.NetworkError.SERIALIZATION->R.string.error_other
        ErrorResult.NetworkError.OTHER -> R.string.error_other
    }
    return UiText.StringResource(error)
}