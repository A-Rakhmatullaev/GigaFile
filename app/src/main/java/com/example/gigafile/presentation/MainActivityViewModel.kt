package com.example.gigafile.presentation

import androidx.lifecycle.ViewModel
import com.example.gigafile.domain.repositories.FileSystemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val fileSystemRepository: FileSystemRepository
): ViewModel() {

}