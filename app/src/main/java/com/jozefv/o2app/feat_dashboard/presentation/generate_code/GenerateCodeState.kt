package com.jozefv.o2app.feat_dashboard.presentation.generate_code

import com.jozefv.o2app.feat_dashboard.domain.util.O2CodeAlias

data class GenerateCodeState(
    val currentGeneratedCode: O2CodeAlias? = null,
    val newGeneratedCode: O2CodeAlias? = null,
    val isGenerateLoading: Boolean = false,
    val isSaveLoading: Boolean = false,
) {
    val isGenerateButtonEnabled = !isGenerateLoading && !isSaveLoading
    val isSaveButtonEnabled = newGeneratedCode != null && currentGeneratedCode != newGeneratedCode
            && !isGenerateLoading && !isSaveLoading
}
