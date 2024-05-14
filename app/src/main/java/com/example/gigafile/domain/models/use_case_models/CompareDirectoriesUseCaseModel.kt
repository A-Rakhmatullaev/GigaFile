package com.example.gigafile.domain.models.use_case_models

import com.example.gigafile.domain.models.core.FileSystemElement

class CompareDirectoriesUseCaseModel (
    val directory1: Pair<List<FileSystemElement>, String>,
    val directory2: Pair<List<FileSystemElement>, String>
)