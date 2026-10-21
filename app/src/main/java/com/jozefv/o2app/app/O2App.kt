package com.jozefv.o2app.app

import android.app.Application
import com.jozefv.o2app.app.di.appModule
import com.jozefv.o2app.core.di.coreModule
import com.jozefv.o2app.feat_dashboard.di.dashboardModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class O2App : Application() {
    val applicationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@O2App)
            modules(
                appModule,
                coreModule,
                dashboardModule
            )
        }
    }
}