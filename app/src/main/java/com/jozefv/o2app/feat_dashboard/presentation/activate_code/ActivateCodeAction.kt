package com.jozefv.o2app.feat_dashboard.presentation.activate_code

sealed interface ActivateCodeAction {
    data object OnActivateClick : ActivateCodeAction
    data object OnBackClick : ActivateCodeAction
}