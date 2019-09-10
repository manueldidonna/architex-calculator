package com.manueldidonna.architexcalc.database

import com.google.common.truth.Truth
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.Before
import org.junit.Test

class ConstantTest {

    private lateinit var constantQueries: ConstantQueries

    @Before
    fun setup() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MyDatabase.Schema.create(driver)
        constantQueries = MyDatabase(driver).constantQueries
    }

    @Test
    fun defaultConstants() {
        Truth.assertThat(constantQueries.selectAll().executeAsList())
            .hasSize(2)
    }

    @Test
    fun defaultConstantsAreNotRemovedByDeleteAll() {
        constantQueries.insert("test1", "2")
        Truth.assertThat(constantQueries.selectAll().executeAsList())
            .hasSize(3)

        constantQueries.deleteAll()
        val onlyDefaultConstants = constantQueries.selectAll().executeAsList()
        Truth.assertThat(onlyDefaultConstants)
            .hasSize(2)

        Truth.assertThat(onlyDefaultConstants.map(Constant::value))
            .containsExactly("pi", "e")
    }
}