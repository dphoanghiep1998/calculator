package com.neko.hiepdph.calculatorvault.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.util.Log
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.Constant.archiveMimeTypes
import com.neko.hiepdph.calculatorvault.common.Constant.extraAudioMimeTypes
import com.neko.hiepdph.calculatorvault.common.Constant.extraDocumentMimeTypes
import com.neko.hiepdph.calculatorvault.common.extensions.getLongValue
import com.neko.hiepdph.calculatorvault.common.extensions.getStringValue
import com.neko.hiepdph.calculatorvault.common.extensions.queryCursor
import com.neko.hiepdph.calculatorvault.data.model.FileItem
import com.neko.hiepdph.calculatorvault.data.model.GroupFile
import java.io.File
import java.util.*

object MediaStoreUtils {
    fun getParentFolderName(context: Context, type: String): List<GroupFile> {
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns._ID
        )
        val folders = mutableMapOf<String, GroupFile>()

        try {
            context.queryCursor(uri, projection) { cursor ->
                try {
                    val fullMimetype = cursor.getStringValue(MediaStore.Files.FileColumns.MIME_TYPE)
                        ?.lowercase(Locale.getDefault()) ?: return@queryCursor
                    val name = cursor.getStringValue(MediaStore.Files.FileColumns.DISPLAY_NAME)
                    val id = cursor.getLongValue(MediaStore.Files.FileColumns._ID)
                    val size = cursor.getLongValue(MediaStore.Files.FileColumns.SIZE)
                    if (size == 0L) {
                        return@queryCursor
                    }

                    val path = cursor.getStringValue(MediaStore.Files.FileColumns.DATA)
                    val lastModified =
                        cursor.getLongValue(MediaStore.Files.FileColumns.DATE_MODIFIED) * 1000

                    val mimetype = fullMimetype.substringBefore("/")

                    when (type) {
                        Constant.TYPE_PICTURE -> {
                            if (mimetype == "image") {

                                val parentFolder = File(path).parentFile?.name ?: "No_name"

                                if (!folders.containsKey(parentFolder)) {
                                    folders[parentFolder] = GroupFile(
                                        parentFolder,
                                        Constant.TYPE_PICTURE,
                                        path,
                                        mutableListOf(),
                                        mutableListOf(),
                                        File(path).parentFile?.path ?: ""

                                    )
                                }
                                if (path.isNotEmpty()) {
                                    folders[parentFolder]?.dataList?.add(
                                        path
                                    )
                                }
                            }
                        }
                        Constant.TYPE_VIDEOS -> {
                            if (mimetype == "video") {

                                val parentFolder = File(path).parentFile?.name ?: "No_name"

                                if (!folders.containsKey(parentFolder)) {
                                    folders[parentFolder] = GroupFile(
                                        parentFolder,
                                        Constant.TYPE_VIDEOS,
                                        path,
                                        mutableListOf(),
                                        mutableListOf(),
                                        File(path).parentFile?.path ?: ""

                                    )
                                }
                                if (path.isNotEmpty()) {
                                    folders[parentFolder]?.dataList?.add(
                                        path
                                    )
                                }
                            }
                        }
                        Constant.TYPE_AUDIOS -> {
                            if (mimetype == "audio" || extraAudioMimeTypes.contains(fullMimetype)) {

                                val parentFolder = File(path).parentFile?.name ?: "No_name"

                                if (!folders.containsKey(parentFolder)) {
                                    folders[parentFolder] = GroupFile(
                                        parentFolder,
                                        Constant.TYPE_AUDIOS,
                                        path,
                                        mutableListOf(),
                                        mutableListOf(),
                                        File(path).parentFile?.path ?: ""

                                    )
                                }
                                if (path.isNotEmpty()) {
                                    folders[parentFolder]?.dataList?.add(
                                        path
                                    )
                                    folders[parentFolder]?.dataThumb?.add(
                                        getThumbnail(path)
                                    )
                                }
                            }
                        }
                        Constant.TYPE_FILE -> {
                            if (mimetype == "text" || extraDocumentMimeTypes.contains(fullMimetype) || archiveMimeTypes.contains(
                                    fullMimetype
                                )
                            ) {
                                val parentFolder =
                                    Constant.FILES_FOLDER_NAME + "utra+super+promax+12345"
                                if (!folders.containsKey(parentFolder)) {
                                    folders[parentFolder] = GroupFile(
                                        parentFolder,
                                        Constant.TYPE_AUDIOS,
                                        path,
                                        mutableListOf(),
                                        mutableListOf(),
                                        File(path).parentFile?.path ?: "",
                                        mutableSetOf()
                                    )
                                }
                                if (path.isNotEmpty()) {
                                    if (name.lowercase(Locale.ROOT).endsWith(Constant.TYPE_PDF)) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_PDF)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_CSV)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_CSV)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_PPT)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_PPT)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_PPTX)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_PPT)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_TEXT)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_TEXT)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_WORD)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_WORD)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_EXCEL)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_EXCEL)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_WORDX)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_WORD)
                                    } else if (name.lowercase(Locale.ROOT)
                                            .endsWith(Constant.TYPE_ZIP)
                                    ) {
                                        folders[parentFolder]?.dataTypeList?.add(Constant.TYPE_ZIP)
                                    }
                                }
                                Log.d(
                                    "TAG",
                                    "getParentFolderName: " + folders[parentFolder]?.dataTypeList
                                )
                            }
                        }

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return folders.values.toList()
    }

    private fun getThumbnail(path: String): Bitmap? {
        return try {
            val mr = MediaMetadataRetriever()
            mr.setDataSource(path)
            val byte1 = mr.embeddedPicture
            mr.release()
            if (byte1 != null) BitmapFactory.decodeByteArray(byte1, 0, byte1.size) else null
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun getChildImageFromPath(context: Context, path: String): List<FileItem> {
        try {

            val listImageChild = mutableListOf<FileItem>()
            val uri = MediaStore.Files.getContentUri("external")
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media._ID
            )
            val selectionArgs = arrayOf("$path/%")
            context.queryCursor(uri, projection, selectionArgs = selectionArgs) { cursor ->
                val id = cursor.getLongValue(MediaStore.Images.Media._ID)
                val childPath = cursor.getStringValue(MediaStore.Images.Media.DATA)
                val size = cursor.getLongValue(MediaStore.Images.Media.SIZE)
                val modified = cursor.getLongValue(MediaStore.Images.Media.SIZE)
                val name = cursor.getStringValue(MediaStore.Images.Media.DISPLAY_NAME)

                listImageChild.add(
                    FileItem(
                        childPath, name, size, modified, id, Constant.TYPE_PICTURE
                    )
                )

            }
            return listImageChild
        } catch (e: Exception) {
            e.printStackTrace()
            return mutableListOf()
        }

    }

    fun getChildVideoFromPath(context: Context, path: String) {
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media._ID
        )
        val selectionArgs = arrayOf("$path/%")
        context.queryCursor(uri, projection, selectionArgs = selectionArgs) { cursor ->
            val id = cursor.getLongValue(MediaStore.Images.Media._ID)
            val childPath = cursor.getLongValue(MediaStore.Images.Media.DATA)
            val size = cursor.getLongValue(MediaStore.Images.Media.SIZE)
            val modified = cursor.getLongValue(MediaStore.Images.Media.SIZE)
            val name = cursor.getStringValue(MediaStore.Images.Media.DISPLAY_NAME)

        }

    }

    fun getChildAudioFromPath(context: Context, path: String) {
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media._ID
        )
        val selectionArgs = arrayOf("$path/%")
        context.queryCursor(uri, projection, selectionArgs = selectionArgs) { cursor ->
            val id = cursor.getLongValue(MediaStore.Images.Media._ID)
            val childPath = cursor.getLongValue(MediaStore.Images.Media.DATA)
            val size = cursor.getLongValue(MediaStore.Images.Media.SIZE)
            val modified = cursor.getLongValue(MediaStore.Images.Media.SIZE)
            val name = cursor.getStringValue(MediaStore.Images.Media.DISPLAY_NAME)

        }

    }

    fun getChildFileFromPath(context: Context, path: String) {
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media._ID
        )
        val selectionArgs = arrayOf("$path/%")
        context.queryCursor(uri, projection, selectionArgs = selectionArgs) { cursor ->
            val id = cursor.getLongValue(MediaStore.Images.Media._ID)
            val childPath = cursor.getLongValue(MediaStore.Images.Media.DATA)
            val size = cursor.getLongValue(MediaStore.Images.Media.SIZE)
            val modified = cursor.getLongValue(MediaStore.Images.Media.SIZE)
            val name = cursor.getStringValue(MediaStore.Images.Media.DISPLAY_NAME)

        }

    }


}