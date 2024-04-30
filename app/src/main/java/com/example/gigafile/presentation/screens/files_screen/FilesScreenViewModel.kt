package com.example.gigafile.presentation.screens.files_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.TestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesScreenViewModel @Inject constructor(
    private val testRepository: TestRepository
): ViewModel() {
    private val directoryData = MutableLiveData<List<FileSystemElement>>()
    val directoryLiveData = directoryData as LiveData<List<FileSystemElement>>

    fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            directoryData.postValue(TestUseCase(testRepository).execute())
        }
    }
}