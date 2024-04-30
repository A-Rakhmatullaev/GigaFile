package com.example.gigafile.data.repositories

import android.os.Environment
import com.example.gigafile.core.extensions.log
import com.example.gigafile.core.extensions.randomId
import com.example.gigafile.domain.models.core.Directory
import com.example.gigafile.domain.models.core.File
import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.repositories.TestRepository

class TestRepositoryImpl: TestRepository {
    override suspend fun directoryData(): List<FileSystemElement> {
        val mainDirectory = Environment.getExternalStorageDirectory()
        val files = mainDirectory.listFiles()

        return arrayListOf<FileSystemElement>().apply {
            if(files != null) {
                if(files.isNotEmpty()) {
                    files.forEach {
                        this.add(
                            if(it.isDirectory) Directory(
                                // TODO: Use randomID() or any other smarter implementation
                                it.absolutePath,
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