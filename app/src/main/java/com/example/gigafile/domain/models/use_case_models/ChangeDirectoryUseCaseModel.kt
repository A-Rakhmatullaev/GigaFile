package com.example.gigafile.domain.models.use_case_models

class ChangeDirectoryUseCaseModel(
    val directoryName: String,
    val directoryPath: String?,
    val storageRootPath: String?
)