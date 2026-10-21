package com.jozefv.o2app.feat_info.presenation.info

sealed interface InfoAction {
    data class OnUriCallException(val exception: Exception) : InfoAction
    data object OnBackClick : InfoAction
}