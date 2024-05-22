package com.example.gigafile.presentation.utils.adapters.directory_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.gigafile.domain.models.core.file_system.FileSystemElement

class DirectoryDiffUtil(
    private val oldDirectories: List<FileSystemElement>,
    private val newDirectories: List<FileSystemElement>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldDirectories.size

    override fun getNewListSize(): Int = newDirectories.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldDirectories[oldItemPosition].id == newDirectories[newItemPosition].id


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if(oldDirectories[oldItemPosition].name != newDirectories[newItemPosition].name) return false
        if(oldDirectories[oldItemPosition].size != newDirectories[newItemPosition].size) return false

        return true
    }
}