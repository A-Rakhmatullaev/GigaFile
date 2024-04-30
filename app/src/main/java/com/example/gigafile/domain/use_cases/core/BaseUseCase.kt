package com.example.gigafile.domain.use_cases.core

interface BaseUseCase {
    suspend fun execute(vararg elements: Any): Any
}