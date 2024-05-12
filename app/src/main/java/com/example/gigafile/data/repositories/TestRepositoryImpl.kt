package com.example.gigafile.data.repositories

import android.os.Environment
import com.example.gigafile.core.extensions.log
import com.example.gigafile.domain.models.core.Directory
import com.example.gigafile.domain.models.core.File
import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.models.core.Storage
import com.example.gigafile.domain.repositories.TestRepository

class TestRepositoryImpl: TestRepository {
    override suspend fun storage(storageId: String): Storage {
        // TODO: remake, because now it pretends to be automated, but just returns internal storage
        // pretend to search from a database (table of storages), and you have automated storage checker,
        // that can check which storages are available (both on the device and cloud)
        if(storageId == "0") {
            val mainDirectory = Environment.getExternalStorageDirectory()
            return Storage("0", "Internal Storage", "1KB", mainDirectory.absolutePath)
        } else {
            throw Exception("No other storages are available right now!")
        }
    }

    // TODO: maybe change directoryPath to directoryId or something
    override suspend fun directoryData(directoryPath: String): List<FileSystemElement> {
        val directory = java.io.File(directoryPath)
        val files = directory.listFiles()

        return arrayListOf<FileSystemElement>().apply {
            if(files != null) {
                if(files.isNotEmpty()) {
                    files.forEach {
                        this.add(
                            if(it.isDirectory) Directory(
                                // TODO: Use randomID() or any other smarter implementation
                                it.absolutePath + it.extension + it.toURI().toString(),
                                it.name,
                                "${it.length()} Bytes",
                                "${it.listFiles()?.size ?: 0}")
                            else File(
                                it.absolutePath,
                                it.name,
                                "${it.length()} Bytes")
                        )
                    }
                } else log("MyLog", "No files")
            } else log("MyLog", "Files are null")
        }
    }
}