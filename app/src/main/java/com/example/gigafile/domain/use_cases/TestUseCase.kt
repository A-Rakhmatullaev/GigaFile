package com.example.gigafile.domain.use_cases

import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase
import kotlinx.coroutines.flow.Flow

class TestUseCase(private val testRepository: TestRepository): BaseUseCase <Unit, Flow<List<FileSystemElement>>> {
    override suspend fun execute(element: Unit): Flow<List<FileSystemElement>> {
        return testRepository.directoryData("")
    }
}