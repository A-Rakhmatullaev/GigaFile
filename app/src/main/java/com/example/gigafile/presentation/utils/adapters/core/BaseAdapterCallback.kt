package com.example.gigafile.presentation.utils.adapters.core

import android.view.View

interface BaseAdapterCallback <T> {
    fun click(item: T, position: Int , view: View)

    fun longClick(item: T, position: Int, view: View)
}