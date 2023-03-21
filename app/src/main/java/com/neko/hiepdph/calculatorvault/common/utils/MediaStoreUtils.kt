package com.neko.hiepdph.calculatorvault.common.utils

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.extensions.getStringValue
import com.neko.hiepdph.calculatorvault.data.model.GroupFile
import java.io.File
import java.util.*

object MediaStoreUtils {
    fun getParentFolder(context: Context, type: String): List<GroupFile> {
        try {
            val projection = arrayOf(
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

            val uri = MediaStore.Files.getContentUri("external")

            val exCursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

            val folders = mutableMapOf<String, GroupFile>()
            Log.d("TAG", "getParentFolder: " + type)

            exCursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val fullMimeType = cursor.getStringValue(MediaStore.Files.FileColumns.MIME_TYPE)
                        ?.lowercase(Locale.getDefault()) ?: return@use
                    val mimetype = fullMimeType.substringBefore("/")

                    val path = cursor.getStringValue(MediaStore.Files.FileColumns.DATA)
                    val folderName =
                        cursor.getStringValue(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                            ?: context.getString(R.string.app_name)
                    val parentFolder = File(path).parentFile?.name ?: ""
                    val displayName = if (parentFolder.isEmpty()) "Ex Storage" else folderName
                    when (type) {
                        Constant.TYPE_PICTURE -> {
                            if (mimetype == "image") {
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
                        Constant.TYPE_AUDIOS -> {
                            if (mimetype == "audio" || Constant.extraAudioMimeTypes.contains(
                                    fullMimeType
                                )
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_AUDIOS,
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
                        Constant.TYPE_DOCUMENT -> {
                            if (mimetype == "text" || Constant.extraDocumentMimeTypes.contains(
                                    fullMimeType
                                )
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_DOCUMENT,
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
                        Constant.TYPE_VIDEOS -> {
                            if (mimetype == "video") {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_VIDEOS,
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
                        else -> {
                            if (mimetype != "image" && mimetype != "video" && mimetype != "audio" && mimetype != "text" && !Constant.extraAudioMimeTypes.contains(
                                    fullMimeType
                                ) && !Constant.extraDocumentMimeTypes.contains(fullMimeType)
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_ADD_MORE,
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
                    }


                }
            }
            return folders.values.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }
    fun getParentFolder(context: Context, type: String): List<GroupFile> {
        try {
            val projection = arrayOf(
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

            val uri = MediaStore.Files.getContentUri("external")

            val exCursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

            val folders = mutableMapOf<String, GroupFile>()
            Log.d("TAG", "getParentFolder: " + type)

            exCursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val fullMimeType = cursor.getStringValue(MediaStore.Files.FileColumns.MIME_TYPE)
                        ?.lowercase(Locale.getDefault()) ?: return@use
                    val mimetype = fullMimeType.substringBefore("/")

                    val path = cursor.getStringValue(MediaStore.Files.FileColumns.DATA)
                    val folderName =
                        cursor.getStringValue(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                            ?: context.getString(R.string.app_name)
                    val parentFolder = File(path).parentFile?.name ?: ""
                    val displayName = if (parentFolder.isEmpty()) "Ex Storage" else folderName
                    when (type) {
                        Constant.TYPE_PICTURE -> {
                            if (mimetype == "image") {
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
                        Constant.TYPE_AUDIOS -> {
                            if (mimetype == "audio" || Constant.extraAudioMimeTypes.contains(
                                    fullMimeType
                                )
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_AUDIOS,
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
                        Constant.TYPE_DOCUMENT -> {
                            if (mimetype == "text" || Constant.extraDocumentMimeTypes.contains(
                                    fullMimeType
                                )
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_DOCUMENT,
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
                        Constant.TYPE_VIDEOS -> {
                            if (mimetype == "video") {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_VIDEOS,
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
                        else -> {
                            if (mimetype != "image" && mimetype != "video" && mimetype != "audio" && mimetype != "text" && !Constant.extraAudioMimeTypes.contains(
                                    fullMimeType
                                ) && !Constant.extraDocumentMimeTypes.contains(fullMimeType)
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_ADD_MORE,
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
                    }


                }
            }
            return folders.values.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

    fun getParentFolder(context: Context, type: String): List<GroupFile> {
        try {
            val projection = arrayOf(
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

            val uri = MediaStore.Files.getContentUri("external")

            val exCursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

            val folders = mutableMapOf<String, GroupFile>()
            Log.d("TAG", "getParentFolder: " + type)

            exCursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val fullMimeType = cursor.getStringValue(MediaStore.Files.FileColumns.MIME_TYPE)
                        ?.lowercase(Locale.getDefault()) ?: return@use
                    val mimetype = fullMimeType.substringBefore("/")

                    val path = cursor.getStringValue(MediaStore.Files.FileColumns.DATA)
                    val folderName =
                        cursor.getStringValue(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                            ?: context.getString(R.string.app_name)
                    val parentFolder = File(path).parentFile?.name ?: ""
                    val displayName = if (parentFolder.isEmpty()) "Ex Storage" else folderName
                    when (type) {
                        Constant.TYPE_PICTURE -> {
                            if (mimetype == "image") {
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
                        Constant.TYPE_AUDIOS -> {
                            if (mimetype == "audio" || Constant.extraAudioMimeTypes.contains(
                                    fullMimeType
                                )
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_AUDIOS,
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
                        Constant.TYPE_DOCUMENT -> {
                            if (mimetype == "text" || Constant.extraDocumentMimeTypes.contains(
                                    fullMimeType
                                )
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_DOCUMENT,
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
                        Constant.TYPE_VIDEOS -> {
                            if (mimetype == "video") {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_VIDEOS,
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
                        else -> {
                            if (mimetype != "image" && mimetype != "video" && mimetype != "audio" && mimetype != "text" && !Constant.extraAudioMimeTypes.contains(
                                    fullMimeType
                                ) && !Constant.extraDocumentMimeTypes.contains(fullMimeType)
                            ) {
                                if (!folders.containsKey(displayName)) {
                                    folders[folderName] = GroupFile(
                                        displayName,
                                        Constant.TYPE_ADD_MORE,
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
                    }


                }
            }
            return folders.values.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

}