package com.manueldidonna.architexcalc.database

import com.google.common.truth.Truth.*
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.Before
import org.junit.Test


class HistoryTest {

    private lateinit var historyQueries: HistoryQueries

    @Before
    fun setup() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MyDatabase.Schema.create(driver)
        historyQueries = MyDatabase(driver).historyQueries
    }

    @Test
    fun insertEntity() {
        historyQueries.insert("4+4", "8")
        val list = historyQueries.selectAll().executeAsList()
        assertThat(list).hasSize(1)
        assertThat(list[0].expression).isEqualTo("4+4")
        assertThat(list[0].result).isEqualTo("8")
    }

    @Test
    fun deleteAllEntities(){
        for(i in 0 until 10){
            historyQueries.insert("$i + $i", "${i*2}")
        }
        assertThat(historyQueries.selectAll().executeAsList()).hasSize(10)
        historyQueries.deleteAll()
        assertThat(historyQueries.selectAll().executeAsList()).isEmpty()
    }

    @Test
    fun deleteEntityById(){
        historyQueries.insert("1+1", "2")
        historyQueries.insert("2+2", "4")
        val entities = historyQueries.selectAll().executeAsList()
        assertThat(entities).hasSize(2)
        historyQueries.deleteById(entities[0].id)
        val updatedEntities = historyQueries.selectAll().executeAsList()
        assertThat(updatedEntities).hasSize(1)
        assertThat(updatedEntities[0].expression).isEqualTo("2+2")
        assertThat(updatedEntities[0].result).isEqualTo("4")
    }
}