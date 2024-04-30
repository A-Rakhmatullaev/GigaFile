package com.example.gigafile.presentation.utils.adapters.directory_adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.gigafile.R
import com.example.gigafile.databinding.DirectoryItemBinding
import com.example.gigafile.domain.models.core.Directory
import com.example.gigafile.domain.models.core.File
import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.presentation.utils.adapters.core.BaseAdapterCallback
import com.example.gigafile.presentation.utils.adapters.core.BaseViewHolder

class DirectoryViewHolder(
    private val binding: DirectoryItemBinding,
    private val callback: BaseAdapterCallback<FileSystemElement>
): RecyclerView.ViewHolder(binding.root), BaseViewHolder<FileSystemElement> {
    override fun bind(item: FileSystemElement, position: Int, vararg values: Any) {
        // TODO: Check how to add icons for all screen sizes
        binding.name.text = item.name
        binding.size.text = item.size
        when(item) {
            is Directory -> {
                binding.icon.setImageResource(R.drawable.ic_test_directory)
            }
            is File -> {}
        }
    }
}