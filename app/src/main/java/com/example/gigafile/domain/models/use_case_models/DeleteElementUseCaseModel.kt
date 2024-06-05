package com.example.gigafile.domain.models.use_case_models

import com.example.gigafile.domain.models.core.file_system.FileSystemElement

class DeleteElementUseCaseModel(
    val element: FileSystemElement,
    val elementPath: ArrayList<String>
)