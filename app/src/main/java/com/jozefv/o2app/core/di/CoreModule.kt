package com.jozefv.o2app.core.di

import com.jozefv.o2app.core.data.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val coreModule = module {
    single { HttpClientFactory.build(CIO.create()) }
}