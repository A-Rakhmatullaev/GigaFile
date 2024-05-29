package com.example.gigafile.data.local.utils

import android.os.Build
import android.os.FileObserver
import androidx.annotation.RequiresApi
import com.example.gigafile.core.extensions.directoryContainsSystemFile
import com.example.gigafile.core.extensions.generateTempId
import com.example.gigafile.core.extensions.isSystemFile
import com.example.gigafile.core.extensions.log
import com.example.gigafile.domain.models.core.file_system.Directory
import com.example.gigafile.domain.models.core.file_system.File
import com.example.gigafile.domain.models.core.file_system.FileSystemElement

class DirectoryObserverV26ToV28(
    private val directoryPath: String,
    private val notify: (List<FileSystemElement>) -> Unit
): FileObserver(directoryPath, ALL_EVENTS) {
    // TODO: How could it be done differently, if I have to keep both versions?
    override fun onEvent(event: Int, path: String?) {
        if(event.isValidEvent() && !path.isNullOrBlank()) {
            val directory = java.io.File(directoryPath)
            notify(listElements(directory))
        }
    }

    override fun finalize() {
        log("MyLog", "DirObserver is finalized")
        super.finalize()
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
class DirectoryObserverV29(
    private val directory: java.io.File,
    private val notify: (List<FileSystemElement>) -> Unit
): FileObserver(directory, ALL_EVENTS) {
    override fun onEvent(event: Int, path: String?) {
        if(event.isValidEvent() && !path.isNullOrBlank()) {
            notify(listElements(directory))
        }
    }

    override fun finalize() {
        log("MyLog", "DirObserver is finalized")
        super.finalize()
    }
}

fun Int.isValidEvent() =
        (this and FileObserver.CREATE != 0) ||
        (this and FileObserver.ATTRIB != 0) ||
        (this and FileObserver.DELETE != 0) ||
        (this and FileObserver.DELETE_SELF != 0) ||
        (this and FileObserver.MODIFY != 0) ||
        (this and FileObserver.MOVED_FROM != 0) ||
        (this and FileObserver.MOVED_TO != 0) ||
        (this and FileObserver.MOVE_SELF != 0)

fun listElements(directory: java.io.File): List<FileSystemElement> {
    val files = directory.listFiles()
    return arrayListOf<FileSystemElement>().apply {
        if(files != null) {
            if(files.isNotEmpty()) {
                files.forEach {
                    this.add(
                        if(it.isDirectory) {
                            Directory(
                                // TODO: Use randomID() or any other smarter implementation
                                it.generateTempId(),
                                it.name,
                                "${it.length()} Bytes",
                                "${it.listFiles()?.size ?: 0}",
                                it.isSystemFile(),
                                it.directoryContainsSystemFile())
                        }
                        else File(
                            it.generateTempId(),
                            it.name,
                            "${it.length()} Bytes",
                            it.isSystemFile())
                    )
                }
            } else log("MyLog", "No files")
        } else log("MyLog", "Files are null")
    }
}