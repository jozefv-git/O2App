package com.jozefv.o2app.feat_dashboard.di


import com.jozefv.o2app.feat_dashboard.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dashboardModule = module {
    viewModelOf(::HomeViewModel)
}