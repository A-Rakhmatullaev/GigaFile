package com.example.gigafile.domain.repositories

import com.example.gigafile.domain.models.core.BaseResult
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.example.gigafile.domain.models.core.file_system.Storage
import kotlinx.coroutines.flow.Flow

interface FileSystemRepository {
    // TODO: sort functions
    suspend fun storage(storageId: String): Storage
    suspend fun directoryData(directoryPath: String): Flow<List<FileSystemElement>>

    // TODO: Remake!!! Change it to use Directory.kt. Also return some type of Result
    suspend fun addDirectory(directoryPath: String): BaseResult<String, String>
    suspend fun editElement(element: FileSystemElement, path: String, newName: String): BaseResult<String, String>
    suspend fun deleteElement(element: FileSystemElement, path: String): BaseResult<String, String>
}