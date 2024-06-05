package com.example.gigafile.data.repositories

import android.os.Build
import android.os.Environment
import android.os.FileObserver
import com.example.gigafile.core.extensions.isFileInUse
import com.example.gigafile.core.extensions.log
import com.example.gigafile.data.local.utils.DirectoryObserverV26ToV28
import com.example.gigafile.data.local.utils.DirectoryObserverV29
import com.example.gigafile.data.local.utils.listElements
import com.example.gigafile.domain.models.core.BaseResult
import com.example.gigafile.domain.models.core.file_system.Directory
import com.example.gigafile.domain.models.core.file_system.File
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.example.gigafile.domain.models.core.file_system.Storage
import com.example.gigafile.domain.repositories.FileSystemRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class LocalFileSystemRepositoryImpl: FileSystemRepository {
    override suspend fun storage(storageId: String): Storage {
        // TODO: remake, because now it pretends to be automated, but just returns internal storage
        //  pretend to search from a database (table of storages), and you have automated storage checker,
        //  that can check which storages are available (both on the device and cloud)
        if(storageId == "0") {
            val mainDirectory = Environment.getExternalStorageDirectory()
            return Storage("0", "Internal Storage", "1KB", mainDirectory.absolutePath)
        } else {
            throw Exception("No other storages are available right now!")
        }
    }

    // TODO: maybe change directoryPath to directoryId or something
    // TODO: check edge cases:
    //  - when directory itself is moved/delete/renamed etc.
    override suspend fun directoryData(directoryPath: String): Flow<List<FileSystemElement>> {
        return callbackFlow {
            val action: (List<FileSystemElement>) -> Unit = {
                trySend(it)
            }

            val fileObserver: FileObserver = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val directory = java.io.File(directoryPath)
                DirectoryObserverV29(directory, action)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                DirectoryObserverV26ToV28(directoryPath, action)
            } else {
                throw Exception("Could not identify Build SDK!")
            }

            fileObserver.startWatching()

            trySend(listElements(java.io.File(directoryPath)))

            awaitClose {
                //log("MyLog", "Flow is closed!")
                fileObserver.stopWatching()
            }
        }.onStart {
            //log("MyLog", "Flow is started")
        }.onCompletion {
            //log("MyLog", "Flow is completed")
        }
    }

    override suspend fun addDirectory(directoryPath: String): BaseResult<String, String> {
        val newDirectory = java.io.File(directoryPath)
        // Create the directory if it doesn't exist
        return if (!newDirectory.exists()) {
            newDirectory.mkdirs() // Returns true if successful, false otherwise
            log("MyLog", "Created directory! - $directoryPath")
            BaseResult.Success("Folder created")
        } else {
            log("MyLog", "Could not create directory")
            BaseResult.Error("Folder already exists")
        }
    }

    // TODO: Should be tested!
    //  It works because name that is attached to the end of path has extension of the file
    override suspend fun editElement(
        element: FileSystemElement,
        path: String,
        newName: String
    ): BaseResult<String, String> {
        log("MyLog", "Old path: $path/${element.name}")
        val fileToEdit = java.io.File(path + "/${element.name}")
        try {
            if(fileToEdit.exists()) {
                if(!fileToEdit.isFileInUse()) {
                    when(element) {
                        is Directory -> {
                            if(element.isSystemElement || element.containsSystemFile) return BaseResult.Error("Impossible to edit")
                        }
                        is File -> {
                            if(element.isSystemElement) return BaseResult.Error("Impossible to edit")
                        }
                        is Storage -> {
                            throw Exception("Trying to edit storage")
                        }
                    }
                    log("MyLog", "New path: $path/${newName}")
                    if(fileToEdit.renameTo(java.io.File(path + "/${newName}")))
                        return BaseResult.Success("Edited!")
                    return BaseResult.Error("Refused to edit")
                }
                return BaseResult.Error("File is in use by other apps")
            } else {
                return BaseResult.Error("File does not exist")
            }
        } catch (e: Exception) {
            log("MyLog", "Exception in editElement(): $e")
            return BaseResult.Error("Exception: Impossible to edit")
        }
    }

    // TODO: Should be tested!
    //  It works because name that is attached to the end of path in the use case has extension of the file
    override suspend fun deleteElement(
        element: FileSystemElement,
        path: String
    ): BaseResult<String, String> {
        try {
            val fileToDelete = java.io.File(path + "/${element.name}")
            if(fileToDelete.exists()) {
                if(!fileToDelete.isFileInUse()) {
                    when(element) {
                        is Directory -> {
                            if(element.isSystemElement || element.containsSystemFile) return BaseResult.Error("Impossible to delete")
                        }
                        is File -> {
                            if(element.isSystemElement) return BaseResult.Error("Impossible to delete")
                        }
                        is Storage -> {
                            throw Exception("Trying to delete storage")
                        }
                    }
                    if(fileToDelete.deleteRecursively())
                        return BaseResult.Success("Deleted!")
                    return BaseResult.Error("Refused to delete")
                }
                return BaseResult.Error("File is in use by other apps")
            } else {
                return BaseResult.Error("File does not exist")
            }
        } catch (e: Exception) {
            log("MyLog", "Exception in deleteElement(): $e")
            return BaseResult.Error("Exception: Impossible to delete")
        }
    }
}