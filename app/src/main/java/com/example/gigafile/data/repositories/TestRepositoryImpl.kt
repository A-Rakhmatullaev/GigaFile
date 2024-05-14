package com.example.gigafile.data.repositories

import android.os.Build
import android.os.Environment
import android.os.FileObserver
import com.example.gigafile.core.extensions.log
import com.example.gigafile.data.local.models.DirectoryObserverV26ToV28
import com.example.gigafile.data.local.models.DirectoryObserverV29
import com.example.gigafile.data.local.models.listElements
import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.models.core.Storage
import com.example.gigafile.domain.repositories.TestRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

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
    // TODO: check edge cases:
    // - when directory itself is moved/delete/renamed etc.
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
}