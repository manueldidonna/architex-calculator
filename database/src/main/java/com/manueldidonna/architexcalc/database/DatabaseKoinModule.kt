package com.manueldidonna.architexcalc.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val DATABASE_NAME = "architexcalc.db"

private fun newDatabaseInstance(context: Context): MyDatabase {
    val driver = AndroidSqliteDriver(MyDatabase.Schema, context, DATABASE_NAME)
    return MyDatabase(driver)
}

val databaseKoinModule = module(override = true) {
    single { newDatabaseInstance(androidApplication()) }
    single { HistoryRepository(get<MyDatabase>().historyQueries) }
    single { ConstantRepository(get<MyDatabase>().constantQueries) }
}
