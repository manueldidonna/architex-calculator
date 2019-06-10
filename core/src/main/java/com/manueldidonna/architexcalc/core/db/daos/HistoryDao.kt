package com.manueldidonna.architexcalc.core.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.manueldidonna.architexcalc.core.db.entities.HistoryEntity
import io.reactivex.Observable

@Dao
interface HistoryDao : BaseEntityDao<HistoryEntity> {
    @Query("SELECT * FROM history")
    fun getHistoryEntities(): Observable<List<HistoryEntity>>

    @Query("DELETE FROM history")
    suspend fun deleteAll()

    @Query("DELETE FROM history where id = :itemId ")
    suspend fun deleteById(itemId: Long)
}