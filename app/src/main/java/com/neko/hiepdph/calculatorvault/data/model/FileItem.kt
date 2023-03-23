package com.neko.hiepdph.calculatorvault.data.model

class FileItem(
    val path: String,
    val name: String,
    var size: Long = 0L,
    var modified: Long = 0L,
    var mediaStoreId: Long = 0L,
    var type: String
) {}