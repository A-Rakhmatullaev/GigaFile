package com.example.gigafile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gigafile.domain.models.core.Data
import com.example.gigafile.domain.repositories.TestRepository
import com.example.gigafile.domain.use_cases.TestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val testRepository: TestRepository
): ViewModel() {
    private val directoryData = MutableLiveData<List<Data>>()
    val directoryLiveData = directoryData as LiveData<List<Data>>

    fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            directoryData.postValue(TestUseCase(testRepository).execute())
        }
    }
}