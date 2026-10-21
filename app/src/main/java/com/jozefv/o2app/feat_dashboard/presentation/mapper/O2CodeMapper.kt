package com.jozefv.o2app.feat_dashboard.presentation.mapper

import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2Code
import com.jozefv.o2app.feat_dashboard.presentation.model.CodeStatusUi

fun CodeStatusUi.toO2Code(): O2Code{
    return O2Code(
        code = code,
        isActive = isActive
    )
}