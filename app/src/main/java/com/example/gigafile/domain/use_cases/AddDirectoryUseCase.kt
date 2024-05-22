package com.example.gigafile.domain.use_cases

import com.example.gigafile.core.extensions.pathFromList
import com.example.gigafile.domain.models.core.BaseResult
import com.example.gigafile.domain.models.use_case_models.AddDirectoryUseCaseModel
import com.example.gigafile.domain.repositories.FileSystemRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class AddDirectoryUseCase(private val fileSystemRepository: FileSystemRepository): BaseUseCase<AddDirectoryUseCaseModel, String> {
    override suspend fun execute(element: AddDirectoryUseCaseModel): String {
        if(element.directoryPath.isEmpty()) return "Directory path is empty"
        if(element.directoryName.isBlank()) return "Directory name is empty"

        val newPath = arrayListOf<String>().apply {
            addAll(element.directoryPath)
        }
        newPath.add(element.directoryName)

        return when(val result = fileSystemRepository.addDirectory(pathFromList(newPath))) {
            is BaseResult.Error -> {result.err}
            is BaseResult.Success -> {result.data}
        }
    }
}