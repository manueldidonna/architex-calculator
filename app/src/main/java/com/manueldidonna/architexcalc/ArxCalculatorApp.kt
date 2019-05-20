package com.manueldidonna.architexcalc

import android.app.Application
import androidx.work.Configuration
import com.jakewharton.threetenabp.AndroidThreeTen
import com.manueldidonna.architexcalc.core.ThemeManager
import com.manueldidonna.architexcalc.core.coreKoinModule
import com.manueldidonna.architexcalc.data.appKoinModule
import com.manueldidonna.architexcalc.history.data.historyKoinModule
import com.manueldidonna.architexcalc.settings.data.settingsKoinModule
import com.manueldidonna.architexcalc.util.TimberKoinLogger
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ArxCalculatorApp : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        // JSR-310 library
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

        // ThemeManager
        get<ThemeManager>().init()
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setMinimumLoggingLevel(android.util.Log.INFO)
        .build()
}