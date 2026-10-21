package com.jozefv.o2app.feat_dashboard.presentation.generate_code

sealed interface GenerateCodeAction {
    data object OnBackClick: GenerateCodeAction
    data object OnGenerateClick: GenerateCodeAction
    data object OnSaveClick: GenerateCodeAction
}