package com.manueldidonna.architexcalc.core

import androidx.room.Room
import com.manueldidonna.architexcalc.core.db.ArxCalculatorDatabase
import com.manueldidonna.architexcalc.core.prefs.PreferencesManager
import com.manueldidonna.architexcalc.core.prefs.RealPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreKoinModule = module(override = true) {
    single<PreferencesManager> { RealPreferencesManager(androidContext()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            ArxCalculatorDatabase::class.java,
            ArxCalculatorDatabase.DATABASE_NAME
        ).build()
    }
    factory { get<ArxCalculatorDatabase>().historyDao() }
}