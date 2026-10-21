package com.jozefv.o2app.app.di

import com.jozefv.o2app.app.O2App
import com.jozefv.o2app.core.data.util.ProductionCoroutineDispatcher
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> { (androidApplication() as O2App).applicationScope }
    single<CoroutineDispatcherProvider> { ProductionCoroutineDispatcher }
}