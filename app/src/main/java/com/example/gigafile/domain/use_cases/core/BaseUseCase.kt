package com.example.gigafile.domain.use_cases.core

interface BaseUseCase <T, K> {
    suspend fun execute(element: T): K
}