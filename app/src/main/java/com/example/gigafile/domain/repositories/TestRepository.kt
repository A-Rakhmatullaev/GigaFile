package com.example.gigafile.domain.repositories

import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.models.core.Storage
import kotlinx.coroutines.flow.Flow

interface TestRepository {
    // TODO: return wrapped Coroutine Flow, and maybe remove 'suspend'
    // TODO: sort functions
    suspend fun storage(storageId: String): Storage
    suspend fun directoryData(directoryPath: String): Flow<List<FileSystemElement>>
}