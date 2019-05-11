package com.manueldidonna.architexcalc.util

import org.koin.core.logger.KOIN_TAG
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class TimberKoinLogger(level: Level = Level.INFO) : Logger(level) {

    override fun log(level: Level, msg: MESSAGE) {
        if (this.level <= level) {
            logOnLevel(msg)
        }
    }

    private fun logOnLevel(msg: MESSAGE) {
        when (this.level) {
            Level.DEBUG -> Timber.d("$KOIN_TAG $msg")
            Level.INFO -> Timber.i("$KOIN_TAG $msg")
            Level.ERROR -> Timber.e("$KOIN_TAG $msg")
        }
    }
}