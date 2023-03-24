package com.neko.hiepdph.calculatorvault.data.model

import android.graphics.Bitmap

class VideoItem(
    val videoPath: String,
    val videoName: String,
    var videoSize: Long,
    var videoModified: Long,
    var videoMediaStoreId: Long,
    var videoDuration: Int,

    ) : ObjectItem(videoPath, videoName, videoSize, videoModified, videoMediaStoreId) {}