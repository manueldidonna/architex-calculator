package com.manueldidonna.architexcalc

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.manueldidonna.architexcalc.core.coreKoinModule
import com.manueldidonna.architexcalc.data.appKoinModule
import com.manueldidonna.architexcalc.history.data.historyKoinModule
import com.manueldidonna.architexcalc.settings.data.settingsKoinModule
import com.manueldidonna.architexcalc.util.TimberKoinLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ArxCalculatorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        // Logger
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        // Koin
        startKoin {
            logger(TimberKoinLogger())
            androidContext(this@ArxCalculatorApp)
            modules(appKoinModule, coreKoinModule, settingsKoinModule, historyKoinModule)
        }
    }
}