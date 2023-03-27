package com.neko.hiepdph.calculatorvault.data.model

open class ObjectItem(
    private val path: String,
    private val name: String,
    private val size: Long = 0L,
    private val modified: Long = 0L,
    private val mediaStoreId: Long = 0L,
)