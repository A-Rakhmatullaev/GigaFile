package com.example.gigafile.domain.use_cases

import com.example.gigafile.core.extensions.pathFromList
import com.example.gigafile.domain.models.core.BaseResult
import com.example.gigafile.domain.models.use_case_models.DeleteElementUseCaseModel
import com.example.gigafile.domain.repositories.FileSystemRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class DeleteElementUseCase(private val fileSystemRepository: FileSystemRepository): BaseUseCase<DeleteElementUseCaseModel, String> {
    override suspend fun execute(element: DeleteElementUseCaseModel): String {
        if(element.element.id.isBlank()) return "ERROR: Element ID is blank!"
        if(element.element.name.isBlank()) return "ERROR: Element name is blank!"
        if(element.elementPath.isEmpty()) return "ERROR: Path is empty!"

        val path = arrayListOf<String>().apply {
            addAll(element.elementPath)
        }

        return when(val result = fileSystemRepository.deleteElement(element.element, pathFromList(path))) {
            is BaseResult.Error -> {result.err}
            is BaseResult.Success -> {result.data}
        }
    }
}