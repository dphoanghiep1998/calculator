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
    fun getParentImageFolder(context: Context): List<GroupFile> {
        try {
            val projection = arrayOf(
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            val uri = MediaStore.Images.Media.getContentUri("external")

            val exCursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

            val folders = mutableMapOf<String, GroupFile>()

            exCursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val path = cursor.getStringValue(MediaStore.Files.FileColumns.DATA)
                    val folderName =
                        cursor.getStringValue(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                            ?: context.getString(R.string.app_name)
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
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

    fun getParentVideosFolder(context: Context): List<GroupFile> {
        try {
            val projection = arrayOf(
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

            val uri = MediaStore.Video.Media.getContentUri("external")

            val exCursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

            val folders = mutableMapOf<String, GroupFile>()

            exCursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val path = cursor.getStringValue(MediaStore.Video.Media.DATA)
                    val folderName =
                        cursor.getStringValue(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
                            ?: context.getString(R.string.app_name)
                    val parentFolder = File(path).parentFile?.name ?: ""
                    val displayName = if (parentFolder.isEmpty()) "Ex Storage" else folderName

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
            return folders.values.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

    fun getParentAudiosFolder(context: Context): List<GroupFile> {
        try {
            val projection = arrayOf(
                MediaStore.Audio.Media.MIME_TYPE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATE_MODIFIED,
                MediaStore.Audio.Media.BUCKET_DISPLAY_NAME
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            val uri = MediaStore.Audio.Media.getContentUri("external")

            val exCursor = context.contentResolver.query(uri, projection, null, null, sortOrder)

            val folders = mutableMapOf<String, GroupFile>()

            exCursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val path = cursor.getStringValue(MediaStore.Audio.Media.DATA)
                    val folderName =
                        cursor.getStringValue(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME)
                            ?: context.getString(R.string.app_name)
                    val parentFolder = File(path).parentFile?.name ?: ""
                    val displayName = if (parentFolder.isEmpty()) "Ex Storage" else folderName

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
            return folders.values.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

    fun getParentDocumentFolder(context: Context): List<GroupFile> {
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

            exCursor?.use { cursor ->
                while (cursor.moveToNext()) {
                    val fullMimetype = cursor.getStringValue(MediaStore.Files.FileColumns.MIME_TYPE)
                        ?.lowercase(Locale.getDefault()) ?: return@use
                    val mimetype = fullMimetype.substringBefore("/")
                    if (mimetype == "text" || Constant.extraDocumentMimeTypes.contains(fullMimetype)) {
                        val path = cursor.getStringValue(MediaStore.Files.FileColumns.DATA)
                        val folderName =
                            cursor.getStringValue(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME)
                                ?: context.getString(R.string.app_name)
                        val parentFolder = File(path).parentFile?.name ?: ""
                        val displayName = if (parentFolder.isEmpty()) "file_name" else folderName

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
            }
            return folders.values.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }
    }


}