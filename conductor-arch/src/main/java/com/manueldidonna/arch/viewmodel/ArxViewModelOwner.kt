package com.manueldidonna.arch.viewmodel

interface ArxViewModelOwner {
    val viewModelStore: ArxViewModelStore
    val ownerId: String
}