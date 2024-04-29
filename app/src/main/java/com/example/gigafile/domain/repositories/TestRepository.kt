package com.example.gigafile.domain.repositories

import com.example.gigafile.domain.models.core.Data

interface TestRepository {
    // TODO: return wrapped Coroutine Flow, and maybe remove 'suspend'
    suspend fun directoryData(): List<Data>
}