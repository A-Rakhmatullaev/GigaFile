package com.example.gigafile.presentation.utils.adapters.directory_adapter

import android.view.View
import com.example.gigafile.presentation.utils.adapters.core.BaseAdapterCallback

interface DirectoryAdapterCallback <T>: BaseAdapterCallback<T> {
    fun actionClick(item: T, position: Int , view: View)
}