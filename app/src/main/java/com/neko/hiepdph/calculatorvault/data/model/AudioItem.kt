package com.neko.hiepdph.calculatorvault.data.model

import android.graphics.Bitmap

class AudioItem(
    val audioPath: String,
    val audioName: String,
    var audioSize: Long,
    var audioModified: Long,
    var audioMediaStoreId: Long,
    var audioDuration: Int,
    var thumbBitmap:Bitmap ? = null,

    ) : ObjectItem(audioPath, audioName, audioSize, audioModified, audioMediaStoreId) {}