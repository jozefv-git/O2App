package com.jozefv.o2app.feat_dashboard.presentation.activate_code

import com.jozefv.o2app.feat_dashboard.presentation.model.CodeStatusUi

data class ActivateCodeState(
    val codeStatusUi: CodeStatusUi? = null,
    val isLoading: Boolean = false
)
