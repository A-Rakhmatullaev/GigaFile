package com.example.gigafile.presentation.screens.files_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.models.core.Storage
import com.example.gigafile.domain.models.use_case_models.ChangeDirectoryUseCaseModel
import com.example.gigafile.domain.models.use_case_models.ChangeStorageUseCaseModel
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.ChangeDirectoryUseCase
import com.example.gigafile.domain.use_cases.ChangeStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesScreenViewModel @Inject constructor(
    private val testRepository: TestRepository
): ViewModel() {
    private val storageData = MutableLiveData<Storage>()
    val storageLiveData = storageData as LiveData<Storage>

    private val directoryPathData = MutableLiveData("")
    val directoryPathLiveData = directoryPathData as LiveData<String>

    private val directoryData = MutableLiveData<List<FileSystemElement>>()
    val directoryLiveData = directoryData as LiveData<List<FileSystemElement>>

    fun changeStorage(storageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            storageData.postValue(ChangeStorageUseCase(testRepository).execute(
                ChangeStorageUseCaseModel(storageId, storageData.value)
            ))
        }
    }

    fun changeDirectory(directoryName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val job = ChangeDirectoryUseCase(testRepository).execute(
                ChangeDirectoryUseCaseModel(
                    directoryName,
                    directoryPathData.value,
                    storageData.value?.rootDirectoryPath
                )
            )
            directoryPathData.postValue(job.second)
            directoryData.postValue(job.first)
        }
    }

    fun itemClicked(element: FileSystemElement) {
        // check type of element
        // depending on if it's directory or file call proper function
        // TODO: remake, just for now call changeDirectory
        // For example, you can hid this behind a use case
        changeDirectory(element.name)
    }

//    fun loadData() {
//        viewModelScope.launch(Dispatchers.Default) {
//            directoryData.postValue(TestUseCase(testRepository).execute(Unit))
//        }
//    }
}