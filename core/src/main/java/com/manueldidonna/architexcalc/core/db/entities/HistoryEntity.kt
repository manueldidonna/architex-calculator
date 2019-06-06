package com.manueldidonna.architexcalc.core.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long = 0,
    @ColumnInfo(name = "expression") val expression: String,
    @ColumnInfo(name = "result") val result: String
) : BaseEntity