package com.neko.hiepdph.calculatorvault.data.model

open class ImageItem(
    val imagePath: String,
    val imageName: String,
    var imageSize: Long = 0L,
    var imageModified: Long = 0L,
    var imageMediaStoreId: Long = 0L,
) : ObjectItem(imagePath, imageName, imageSize, imageModified, imageMediaStoreId)