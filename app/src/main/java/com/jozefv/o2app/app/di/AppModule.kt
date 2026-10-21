package com.jozefv.o2app.app.di

import com.jozefv.o2app.core.data.util.ProductionCoroutineDispatcher
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import org.koin.dsl.module

val appModule = module {
    single<CoroutineDispatcherProvider> { ProductionCoroutineDispatcher }
}