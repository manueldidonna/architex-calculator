package com.manueldidonna.architexcalc.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manueldidonna.architexcalc.core.db.daos.HistoryDao
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class ArxCalculatorDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        const val DATABASE_NAME = "arxcalc.db"
    }
}