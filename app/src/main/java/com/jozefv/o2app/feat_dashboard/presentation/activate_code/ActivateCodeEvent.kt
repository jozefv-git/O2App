package com.jozefv.o2app.feat_dashboard.presentation.activate_code

import com.jozefv.o2app.core.presentation.util.UiText

sealed interface ActivateCodeEvent {
    data object ActivationSuccess : ActivateCodeEvent
    data class Error(val error: UiText) : ActivateCodeEvent
}