package com.example.gigafile.domain.use_cases

import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.models.use_case_models.CompareDirectoriesUseCaseModel
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class CompareDirectoriesUseCase: BaseUseCase<CompareDirectoriesUseCaseModel, Boolean> {
    override suspend fun execute(element: CompareDirectoriesUseCaseModel): Boolean {
        if(element.directory1.second != element.directory2.second) return false
        if(element.directory1.first != element.directory2.first) return false
        return true
    }
}