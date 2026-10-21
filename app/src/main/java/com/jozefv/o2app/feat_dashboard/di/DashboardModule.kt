package com.jozefv.o2app.feat_dashboard.di


import com.jozefv.o2app.core.data.HttpClientFactory
import com.jozefv.o2app.feat_dashboard.data.o2.Ktor02Api
import com.jozefv.o2app.feat_dashboard.data.o2.O2Repository
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2Api
import com.jozefv.o2app.feat_dashboard.domain.util.o2.Repository
import com.jozefv.o2app.feat_dashboard.presentation.activate_code.ActivateCodeViewModel
import com.jozefv.o2app.feat_dashboard.presentation.generate_code.GenerateCodeViewModel
import com.jozefv.o2app.feat_dashboard.presentation.home.HomeViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dashboardModule = module {
    single { HttpClientFactory.build(CIO.create()) }
    singleOf(::Ktor02Api).bind<O2Api>()
    singleOf(::O2Repository).bind<Repository>()
    viewModelOf(::HomeViewModel)
    viewModelOf(::GenerateCodeViewModel)
    viewModelOf(::ActivateCodeViewModel)
}