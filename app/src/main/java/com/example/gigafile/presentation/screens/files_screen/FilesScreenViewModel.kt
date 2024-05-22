package com.example.gigafile.presentation.screens.files_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigafile.core.extensions.log
import com.example.gigafile.core.extensions.pathFromList
import com.example.gigafile.domain.models.core.file_system.Directory
import com.example.gigafile.domain.models.core.file_system.File
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.example.gigafile.domain.models.core.file_system.Storage
import com.example.gigafile.domain.models.use_case_models.AddDirectoryUseCaseModel
import com.example.gigafile.domain.models.use_case_models.ChangeDirectoryUseCaseModel
import com.example.gigafile.domain.models.use_case_models.ChangeStorageUseCaseModel
import com.example.gigafile.domain.models.use_case_models.DirectoryAction
import com.example.gigafile.domain.repositories.FileSystemRepository
import com.example.gigafile.domain.use_cases.AddDirectoryUseCase
import com.example.gigafile.domain.use_cases.ChangeDirectoryUseCase
import com.example.gigafile.domain.use_cases.ChangeStorageUseCase
import com.example.gigafile.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesScreenViewModel @Inject constructor(
    private val fileSystemRepository: FileSystemRepository
): ViewModel() {
    private val storageData = MutableLiveData<Storage>()
    val storageLiveData = storageData as LiveData<Storage>

    private val directoryPathData = MutableLiveData(arrayListOf<String>())
    val directoryPathLiveData = directoryPathData as LiveData<ArrayList<String>>

    private val repositoryNotificationData = SingleLiveEvent<String>()
    val repositoryNotificationLiveData = repositoryNotificationData as LiveData<String>

    private var dataFlowJob: Job? = null

    private val directoryData = MutableLiveData<List<FileSystemElement>>()
    val directoryLiveData = directoryData as LiveData<List<FileSystemElement>>

    fun changeStorage(storageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            storageData.postValue(ChangeStorageUseCase(fileSystemRepository).execute(
                ChangeStorageUseCaseModel(storageId, storageData.value)
            ))
        }
    }

    fun changeDirectory(directoryAction: DirectoryAction) {
        dataFlowJob?.cancel()

        dataFlowJob = viewModelScope.launch(Dispatchers.Default) {
            val job = ChangeDirectoryUseCase(fileSystemRepository).execute(
                ChangeDirectoryUseCaseModel(
                    directoryAction,
                    directoryPathData.value ?: arrayListOf(),
                    storageData.value?.rootDirectoryPath
                )
            )

            directoryPathData.postValue(job.second)

            job.first.collect {
                directoryData.postValue(it)
                log("MyLog", "Dir in VM: ${directoryPathData.value}")
            }
        }
    }

    fun addDirectory(directoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            log("MyLog", "VM old path: ${directoryPathData.value}")
//            val notify: (message: String) -> Unit = {
//                repositoryNotificationData.postValue(it)
//            }
            repositoryNotificationData.postValue(AddDirectoryUseCase(fileSystemRepository).execute(AddDirectoryUseCaseModel(
                directoryName, directoryPathData.value ?: arrayListOf()
            )))
        }
    }

    fun onElementClicked(element: FileSystemElement) {
        // check type of element
        // depending on if it's directory or file call proper function
        // TODO: remake, just for now call changeDirectory
        // For example, you can hid this behind a use case
        // TODO: remake, make use of ids instead of names!
        when(element) {
            is Directory -> {
                changeDirectory(DirectoryAction.ToDirectory(element.name))
            }
            is File -> {
                log("MyLog", "Open file ${element.name}")
            }
            is Storage -> {}
        }
    }

    fun currentDirectoryIsRoot(): Boolean {
        return (storageData.value != null) &&
                (directoryPathData.value != null) &&
                (storageData.value?.rootDirectoryPath == pathFromList(directoryPathData.value ?: arrayListOf()))
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}