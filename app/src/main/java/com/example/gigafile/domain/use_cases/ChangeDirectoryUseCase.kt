package com.example.gigafile.domain.use_cases

import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.models.use_case_models.ChangeDirectoryUseCaseModel
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class ChangeDirectoryUseCase(private val testRepository: TestRepository): BaseUseCase <
        ChangeDirectoryUseCaseModel,
        Pair<List<FileSystemElement>, String>> {
    override suspend fun execute(element: ChangeDirectoryUseCaseModel): Pair<List<FileSystemElement>, String> {
        if(element.storageRootPath.isNullOrBlank()) throw Exception("No root path is available!")

        // TODO: remake, and remove comments
        // return values of the directory and new current directoryPath
        if(element.directoryPath.isNullOrBlank()) {
            if(element.directoryName.isBlank()) {
                // retrieve values from storage root path
                return Pair(testRepository.directoryData(element.storageRootPath), element.storageRootPath)
            } else {
                // error? because there is no directoryPath but directoryName is chose
                throw Exception("Directory name exists but no directory path is passed!")
            }
        } else {
            return if(element.directoryName.isBlank()) {
                // retrieve values from the same directory path
                Pair(testRepository.directoryData(element.directoryPath), element.directoryPath)
            } else {
                // append name and path, retrieve values from this directory
                val newPath = "${element.directoryPath}/${element.directoryName}"
                Pair(testRepository.directoryData(newPath), newPath)
            }
        }
    }
}