package com.example.gigafile.domain.use_cases

import com.example.gigafile.domain.models.core.Data
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class TestUseCase(private val testRepository: TestRepository): BaseUseCase {
    override suspend fun execute(vararg elements: Any): List<Data> {
        return testRepository.directoryData()
    }
}