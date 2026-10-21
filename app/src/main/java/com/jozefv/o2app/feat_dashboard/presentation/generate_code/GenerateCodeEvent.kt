package com.jozefv.o2app.feat_dashboard.presentation.generate_code

sealed interface GenerateCodeEvent {
    data object SaveSuccess: GenerateCodeEvent
}