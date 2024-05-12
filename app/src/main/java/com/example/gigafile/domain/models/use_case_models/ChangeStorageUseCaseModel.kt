package com.example.gigafile.domain.models.use_case_models

import com.example.gigafile.domain.models.core.Storage

class ChangeStorageUseCaseModel(
    val storageId: String,
    val storage: Storage?
)