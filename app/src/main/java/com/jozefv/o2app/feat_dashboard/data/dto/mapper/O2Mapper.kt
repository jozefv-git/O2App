package com.jozefv.o2app.feat_dashboard.data.dto.mapper

import com.jozefv.o2app.feat_dashboard.data.dto.O2Dto
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2

/**
 * @throws NumberFormatException if the [android] string cannot be parsed to int.
 */
fun O2Dto.toO2(): O2 {
    return O2(android = android.toInt())
}