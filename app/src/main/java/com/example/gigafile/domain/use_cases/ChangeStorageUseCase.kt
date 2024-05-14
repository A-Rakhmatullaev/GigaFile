package com.example.gigafile.domain.use_cases

import com.example.gigafile.domain.models.core.Storage
import com.example.gigafile.domain.models.use_case_models.ChangeStorageUseCaseModel
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.core.BaseUseCase

class ChangeStorageUseCase(private val testRepository: TestRepository): BaseUseCase<ChangeStorageUseCaseModel, Storage> {
    override suspend fun execute(element: ChangeStorageUseCaseModel): Storage {
        // TODO: remove comments
        if(element.storage == null) {
            if(element.storageId.isBlank()) {
                // retrieve internal storage by default, suppose internal storage is always available
                // TODO: automate it, try to automatically search for all available storages, without that
                // internal storage is always available
                return testRepository.storage("0")
            } else {
                // error? because there is storageId and no storage is picked
                throw Exception("Storage exists but no storageId is passed!")
            }
        } else {
            return if(element.storageId.isBlank()) {
                // retrieve last saved storage
                testRepository.storage(element.storage.id)
            } else {
                // change directory within storage
                testRepository.storage(element.storageId)
            }
        }
    }
}