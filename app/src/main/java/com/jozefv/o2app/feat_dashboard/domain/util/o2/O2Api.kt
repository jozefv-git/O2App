package com.jozefv.o2app.feat_dashboard.domain.util.o2

import com.jozefv.o2app.core.domain.util.ErrorResult
import com.jozefv.o2app.core.domain.util.ResultHandler
import com.jozefv.o2app.feat_dashboard.domain.util.O2CodeAlias

interface O2Api {
    suspend fun activateCode(code: O2CodeAlias): ResultHandler<O2, ErrorResult.NetworkError>
}