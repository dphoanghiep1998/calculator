package com.neko.hiepdph.calculatorvault.common.utils

import android.content.Context
import android.graphics.Picture
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.extensions.getStringValue
import com.neko.hiepdph.calculatorvault.data.model.CustomFolder
import com.neko.hiepdph.calculatorvault.data.model.GroupFile
import java.io.File

object MediaStoreUtils {

    fun getParentFolder(context: Context): List<GroupFile> {
        val projection = arrayOf(MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID,MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val externalUri = if(buildMinVersionQ()) MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL) else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val exCursor =
            context.contentResolver.query(externalUri, projection, selection, selectionArgs, sortOrder)

        val folders = mutableMapOf<String, GroupFile>()
        exCursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val path = cursor.getStringValue(MediaStore.Images.Media.DATA)
                val folderName = cursor.getStringValue(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)?:context.getString(R.string.app_name)
                val parentFolder = File(path).parentFile?.name ?: ""
                val displayName = if (parentFolder.isEmpty()) "Ex Storage" else folderName
                if (!folders.containsKey(displayName)) {
                    folders[folderName] = GroupFile(
                        displayName,
                        Constant.TYPE_PICTURE,
                        path,
                        mutableListOf(),
                        File(path).path ?: ""
                    )
                }
                if (path.isNotEmpty()) {
                    folders[displayName]?.dataPathList?.add(path)
                }
            }
        }

        return folders.values.toList()
    }
}