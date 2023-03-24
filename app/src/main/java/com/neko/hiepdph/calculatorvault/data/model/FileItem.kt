package com.neko.hiepdph.calculatorvault.data.model

class FileItem(
    val filePath: String,
    val fileName: String,
    var fileSize: Long,
    var fileModified: Long,
    var fileMediaStoreId: Long,
    var fileType: String,
    var fileDuration: Int? = null,

    ) : ObjectItem(filePath, fileName, fileSize, fileModified, fileMediaStoreId) {}