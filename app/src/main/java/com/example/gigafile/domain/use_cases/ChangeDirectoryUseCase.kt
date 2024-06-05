package com.example.gigafile.domain.use_cases

import com.example.gigafile.core.extensions.pathFromList
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.example.gigafile.domain.models.use_case_models.ChangeDirectoryUseCaseModel
import com.example.gigafile.domain.models.use_case_models.DirectoryAction
import com.example.gigafile.domain.repositories.FileSystemRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase
import kotlinx.coroutines.flow.Flow

class ChangeDirectoryUseCase(private val fileSystemRepository: FileSystemRepository): BaseUseCase <
        ChangeDirectoryUseCaseModel,
        Pair<Flow<List<FileSystemElement>>, ArrayList<String>>> {
    override suspend fun execute(element: ChangeDirectoryUseCaseModel): Pair<Flow<List<FileSystemElement>>, ArrayList<String>> {
        if(element.storageRootPath.isNullOrBlank()) throw Exception("No root path is available!")

        when(element.directoryAction) {
            is DirectoryAction.ToRoot -> {
                return Pair(fileSystemRepository.directoryData(element.storageRootPath), arrayListOf(element.storageRootPath))
            }
            is DirectoryAction.UpToPrevious -> {
                val newPath = element.directoryPath
                if(newPath.size > 1)
                    newPath.removeLast()
                return Pair(fileSystemRepository.directoryData(pathFromList(newPath)), newPath)
            }
            is DirectoryAction.ToDirectory -> {
                // TODO: remake, and remove comments
                //  return values of the directory and new current directoryPath
                if(element.directoryPath.isEmpty()) {
                    if(element.directoryAction.directoryName.isBlank()) {
                        // retrieve values from storage root path
                        return Pair(fileSystemRepository.directoryData(element.storageRootPath), arrayListOf(element.storageRootPath))
                    } else {
                        // error? because there is no directoryPath but directoryName is chose
                        throw Exception("Directory name exists but no directory path is passed!")
                    }
                } else {
                    return if(element.directoryAction.directoryName.isBlank()) {
                        // retrieve values from the same directory path
                        Pair(fileSystemRepository.directoryData(pathFromList(element.directoryPath)), element.directoryPath)
                    } else {
                        // append name and path, retrieve values from this directory
                        val newPath = element.directoryPath
                        newPath.add(element.directoryAction.directoryName)
                        Pair(fileSystemRepository.directoryData(pathFromList(newPath)), newPath)
                    }
                }
            }
            is DirectoryAction.ToHiddenDirectory -> {
                // element.directoryAction.directoryName is actually a directoryPath
                if(element.directoryAction.directoryName.isBlank()) throw Exception("No internal file, app isn't installed")
                // TODO: Remake extension function, that creates arraylist from string, and puts its value as first value
                return Pair(fileSystemRepository.directoryData(element.directoryAction.directoryName), arrayListOf<String>().apply { add(element.directoryAction.directoryName) } )
            }
        }
    }
}