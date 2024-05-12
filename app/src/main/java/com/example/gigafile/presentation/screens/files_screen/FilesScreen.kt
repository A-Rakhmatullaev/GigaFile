package com.example.gigafile.presentation.screens.files_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.core.extensions.log
import com.example.gigafile.databinding.FragmentFilesBinding
import com.example.gigafile.domain.models.core.FileSystemElement
import com.example.gigafile.presentation.utils.adapters.core.BaseAdapterCallback
import com.example.gigafile.presentation.utils.adapters.directory_adapter.DirectoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilesScreen: Fragment(), BaseScreen {
    private val viewModel by viewModels<FilesScreenViewModel>()
    private lateinit var binding: FragmentFilesBinding

    private val directoryAdapter = DirectoryAdapter()
    private val directoryAdapterCallback = DirectoryAdapterCallback()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = directoryAdapter
        directoryAdapter.callback(directoryAdapterCallback)

        viewModel.changeStorage("")
        //viewModel.loadData()
    }

    override fun initObservers() {
        viewModel.storageLiveData.observe(viewLifecycleOwner) { storage ->
            log("MyLog", "Storage: $storage")
            viewModel.changeDirectory("")
        }

        viewModel.directoryPathLiveData.observe(viewLifecycleOwner) { directoryPath ->
            log("MyLog", "directory path: $directoryPath")
        }

        viewModel.directoryLiveData.observe(viewLifecycleOwner) { data ->
            log("MyLog", "Data size: ${data.size}")
            directoryAdapter.data(data)
            data.forEach {
                log("MyLog", "I have: ${it.name} - ${it.size} - ${it.id}")
            }
        }
    }

    inner class DirectoryAdapterCallback: BaseAdapterCallback<FileSystemElement> {
        override fun click(item: FileSystemElement, position: Int, view: View) {
            viewModel.itemClicked(item)
        }

        override fun longClick(item: FileSystemElement, position: Int, view: View) {
            TODO("Not yet implemented")
        }
    }
}