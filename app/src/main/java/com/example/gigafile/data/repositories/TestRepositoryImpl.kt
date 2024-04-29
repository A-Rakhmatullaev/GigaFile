package com.example.gigafile.data.repositories

import com.example.gigafile.domain.models.File
import com.example.gigafile.domain.models.core.Data
import com.example.gigafile.domain.repositories.TestRepository

class TestRepositoryImpl: TestRepository {
    override suspend fun directoryData(): List<Data> {
        return buildList {
            repeat(10) {
                File("File$it", "$it")
            }
        }
    }
}