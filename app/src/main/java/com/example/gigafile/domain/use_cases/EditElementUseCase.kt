package com.example.gigafile.domain.use_cases

import com.example.gigafile.core.extensions.pathFromList
import com.example.gigafile.domain.models.core.BaseResult
import com.example.gigafile.domain.models.use_case_models.EditElementUseCaseModel
import com.example.gigafile.domain.repositories.FileSystemRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class EditElementUseCase(private val fileSystemRepository: FileSystemRepository): BaseUseCase<EditElementUseCaseModel, String> {
    override suspend fun execute(element: EditElementUseCaseModel): String {
        if(element.element.id.isBlank()) return "ERROR: Element ID is blank!"
        if(element.element.name.isBlank()) return "ERROR: Element name is blank!"
        if(element.elementPath.isEmpty()) return "ERROR: Path is empty!"
        if(element.newName.isBlank()) return "Name can't be blank"

        val path = arrayListOf<String>().apply {
            addAll(element.elementPath)
        }

        return when(val result = fileSystemRepository.editElement(element.element, pathFromList(path), element.newName)) {
            is BaseResult.Error -> {result.err}
            is BaseResult.Success -> {result.data}
        }
    }
}