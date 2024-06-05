package com.example.gigafile.presentation.utils.adapters.directory_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gigafile.R
import com.example.gigafile.databinding.DirectoryItemBinding
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.example.gigafile.presentation.utils.adapters.core.BaseAdapter
import com.example.gigafile.presentation.utils.adapters.core.BaseAdapterCallback

class DirectoryAdapter : RecyclerView.Adapter<DirectoryViewHolder>(), BaseAdapter<FileSystemElement> {
    private var directories: List<FileSystemElement> = listOf()
    private lateinit var callback: DirectoryAdapterCallback<FileSystemElement>

    private lateinit var diffUtil: DiffUtil.Callback
    private lateinit var diffResult: DiffUtil.DiffResult

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryViewHolder =
        DirectoryViewHolder(
            DirectoryItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.directory_item, parent, false)),
            callback
        )

    override fun getItemCount(): Int = directories.size

    override fun onBindViewHolder(holder: DirectoryViewHolder, position: Int) {
        holder.bind(directories[position], position)
    }

    override fun data(newItems: Collection<FileSystemElement>) {
        diffUtil = DirectoryDiffUtil(directories, newItems as List<FileSystemElement>)
        diffResult = DiffUtil.calculateDiff(diffUtil)
        directories = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun data(): Collection<FileSystemElement> = directories

    override fun callback(callback: BaseAdapterCallback<FileSystemElement>) {
        this.callback = callback as DirectoryAdapterCallback<FileSystemElement>
    }
}