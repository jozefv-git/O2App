package com.jozefv.o2app.app

import android.app.Application
import com.jozefv.o2app.app.di.appModule
import com.jozefv.o2app.feat_dashboard.di.dashboardModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class O2App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@O2App)
            modules(
                appModule,
                dashboardModule
            )
        }
    }
}