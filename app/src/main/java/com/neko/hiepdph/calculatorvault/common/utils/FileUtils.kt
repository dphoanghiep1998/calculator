package com.neko.hiepdph.calculatorvault.common.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

interface IDeleteFile {
    fun onSuccess()
    fun onFailed()

}

interface ICreateFile {
    fun onSuccess()
    fun onFailed()
}

interface IMoveFile {
    fun onSuccess()
    fun onFailed()

    fun onDoneWithWarning()
}

object FileUtils {
    fun generateFile(context: Context, fileName: String): File? {
        val csvFile = File(context.filesDir, fileName)
        csvFile.createNewFile()
        return if (csvFile.exists()) {
            csvFile
        } else {
            null
        }
    }


    fun shareFileIntent(context: Context, file: File): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        val contentUri =
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val mimType = context.contentResolver.getType(contentUri)
        intent.setDataAndType(contentUri, mimType)
        intent.putExtra(Intent.EXTRA_STREAM, contentUri)
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

        return intent
    }

    fun createSecretFile(parentDir: File, name: String, callback: ICreateFile) {
        try {
            val folder = File(parentDir, name)
            if (!folder.exists()) {
                folder.mkdir()
                callback.onSuccess()
            } else {
                callback.onFailed()
            }
        } catch (e: Exception) {
            callback.onFailed()
            e.printStackTrace()
        }

    }

    fun getFoldersInDirectory(dir: String): List<File> {
        return try {
            val listOfFolder = mutableListOf<File>()
            val directory = File(dir)
            val files = directory.listFiles { file -> file.isDirectory }
            for (file in files) {
                listOfFolder.add(file)
            }
            listOfFolder
        } catch (e: Exception) {
            mutableListOf()
        }

    }

    fun deleteFolderInDirectory(pathFolder: String, callback: IDeleteFile) {
        try {
            val folder = File(pathFolder)
            val delete = folder.deleteRecursively()
            if (delete) {
                callback.onSuccess()
            } else {
                callback.onFailed()
            }
        } catch (e: Exception) {
            callback.onFailed()
            e.printStackTrace()
        }
    }

    fun copyMoveTo(
        filePath: List<String>, destinationPath: String, isCopy: Boolean, callback: IMoveFile
    ) {
        try {
            val directory = File(destinationPath)

            filePath.forEach {
                val sourceFile = File(it)

            }
        } catch (e: Exception) {

        }
    }
}