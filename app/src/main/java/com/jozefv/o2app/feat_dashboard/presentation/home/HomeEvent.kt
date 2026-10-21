package com.jozefv.o2app.feat_dashboard.presentation.home

import com.jozefv.o2app.core.presentation.util.UiText

interface HomeEvent {
    data object Activate : HomeEvent
    data class Error(val error: UiText) : HomeEvent
}