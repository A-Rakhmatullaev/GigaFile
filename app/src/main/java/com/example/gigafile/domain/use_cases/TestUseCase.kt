package com.example.gigafile.domain.use_cases

import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class TestUseCase(private val testRepository: TestRepository): BaseUseCase <Unit, List<FileSystemElement>> {
    override suspend fun execute(element: Unit): List<FileSystemElement> {
        return testRepository.directoryData("")
    }
}