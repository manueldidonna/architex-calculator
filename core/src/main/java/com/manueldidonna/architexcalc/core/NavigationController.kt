package com.manueldidonna.architexcalc.core

interface NavigationController{
    fun <N> getNavigator(): N
}