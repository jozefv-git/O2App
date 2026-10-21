package com.jozefv.o2app.core.presentation.util

import androidx.compose.ui.platform.UriHandler

fun UriHandler.toSafeUriOpener(uri: String): Exception?{
    return try {
        openUri(uri)
        null
    } catch (e: Exception){
        e
    }
}