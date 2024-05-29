package com.example.gigafile.domain.use_cases

import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.example.gigafile.domain.repositories.FileSystemRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase
import kotlinx.coroutines.flow.Flow

// TODO: Delete
class TestUseCase(private val fileSystemRepository: FileSystemRepository): BaseUseCase <Unit, Flow<List<FileSystemElement>>> {
    override suspend fun execute(element: Unit): Flow<List<FileSystemElement>> {
        return fileSystemRepository.directoryData("")
    }
}