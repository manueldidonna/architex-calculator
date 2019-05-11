package com.manueldidonna.architexcalc.core.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import io.reactivex.Flowable

@Dao
interface HistoryDao : BaseEntityDao<HistoryEntity> {
    @Query("SELECT * FROM history")
    fun getHistoryEntities(): Flowable<List<HistoryEntity>>

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}