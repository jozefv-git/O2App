package com.jozefv.o2app.feat_info.di

import com.jozefv.o2app.feat_info.presenation.info.InfoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val infoModule = module {
    viewModelOf(::InfoViewModel)
}