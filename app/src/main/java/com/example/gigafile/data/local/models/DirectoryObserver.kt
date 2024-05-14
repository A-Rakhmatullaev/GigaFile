package com.example.gigafile.data.local.models

import android.os.Build
import android.os.FileObserver
import androidx.annotation.RequiresApi
import com.example.gigafile.core.extensions.log
import com.example.gigafile.domain.models.core.Directory
import com.example.gigafile.domain.models.core.File
import com.example.gigafile.domain.models.core.FileSystemElement

class DirectoryObserverV26ToV28(
    private val directoryPath: String,
    private val action: (List<FileSystemElement>) -> Unit
): FileObserver(directoryPath, ALL_EVENTS) {
    // TODO: How could it be done differently, if I have to keep both versions?
    override fun onEvent(event: Int, path: String?) {
        if(event.isValidEvent() && !path.isNullOrBlank()) {
            val directory = java.io.File(directoryPath)
            action(listElements(directory))
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
    private val action: (List<FileSystemElement>) -> Unit
): FileObserver(directory, ALL_EVENTS) {
    override fun onEvent(event: Int, path: String?) {
        if(event.isValidEvent() && !path.isNullOrBlank()) {
            action(listElements(directory))
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