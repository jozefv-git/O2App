package com.jozefv.o2app.feat_info.presenation.info

import com.jozefv.o2app.core.presentation.util.UiText

sealed interface InfoEvent {
    data class Error(val error: UiText): InfoEvent
}