package com.example.gigafile.core.bases

import androidx.fragment.app.FragmentManager

interface BaseBottomSheet {
    fun <T, K> showBottomSheet(fragmentManager: FragmentManager, argument: T, action: (argument: K) -> Unit)
    fun closeBottomSheet()
}