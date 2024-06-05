package com.example.gigafile.presentation.screens.files_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.core.extensions.hide
import com.example.gigafile.core.extensions.log
import com.example.gigafile.core.extensions.show
import com.example.gigafile.core.extensions.toast
import com.example.gigafile.databinding.FragmentFilesBinding
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.example.gigafile.domain.models.use_case_models.DirectoryAction
import com.example.gigafile.presentation.utils.FilesOnActionModeItemClickCallback
import com.example.gigafile.presentation.utils.adapters.directory_adapter.DirectoryAdapter
import com.example.gigafile.presentation.utils.adapters.directory_adapter.DirectoryAdapterCallback
import com.example.gigafile.presentation.utils.startFilesPrimaryActionMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilesScreen: Fragment(), BaseScreen {
    private val viewModel by viewModels<FilesScreenViewModel>()
    private lateinit var binding: FragmentFilesBinding
    private lateinit var navController: NavController
    private lateinit var destinationChangeListener: NavController.OnDestinationChangedListener
    private var currentDestinationId: String = ""

    private val directoryAdapter = DirectoryAdapter()
    private val directoryAdapterCallbackImpl = DirectoryAdapterCallbackImpl()
    private val popupMenuActions = PopupActions()
    private val filesBottomSheet = FilesBottomSheetImpl()
    private val fileActionModeCallback = FilesOnActionModeItemClickCallbackImpl()

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
        initNavController()
        initViews()
        initObservers()
    }

    private fun initNavController() {
        navController = findNavController()
        destinationChangeListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            val newDestinationId = destination.id.toString()
            if (currentDestinationId.isNotBlank() && newDestinationId == currentDestinationId) {
                // The destination has not changed
                log("MyLog", "Destination is the same: $newDestinationId")
                viewModel.changeDirectory(DirectoryAction.ToRoot(""))
            } else {
                // The destination has changed
                log("MyLog", "New destination: $newDestinationId")
                currentDestinationId = newDestinationId
            }
        }
        // Add destination change listener
        navController.addOnDestinationChangedListener(destinationChangeListener)
    }

    override fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = directoryAdapter
        directoryAdapter.callback(directoryAdapterCallbackImpl)

        binding.upButton.hide()
        binding.upButton.setOnClickListener {
            viewModel.changeDirectory(DirectoryAction.UpToPrevious(""))
        }

        binding.addButton.setOnClickListener {
            filesBottomSheet.showBottomSheet(FilesBottomSheetTypes.CREATE, childFragmentManager, Unit) { name: String ->
                viewModel.addDirectory(name)
            }
        }

        viewModel.changeStorage("")
    }

    override fun initObservers() {
        viewModel.storageLiveData.distinctUntilChanged().observe(viewLifecycleOwner) { storage ->
            log("MyLog", "Storage: $storage")
            viewModel.changeDirectory(DirectoryAction.ToDirectory(""))
        }

        viewModel.directoryPathLiveData.observe(viewLifecycleOwner) { directoryPath ->
            log("MyLog", "directory path: $directoryPath")
            if(viewModel.currentDirectoryIsRoot()) binding.upButton.hide()
            else binding.upButton.show()
        }

        viewModel.directoryLiveData.distinctUntilChanged().observe(viewLifecycleOwner) { data ->
            log("MyLog", "Data size: ${data.size}")
            directoryAdapter.data(data)
        }

        viewModel.repositoryNotificationLiveData.observe(viewLifecycleOwner) { message ->
            this.binding.root.toast(message)
            // TODO: Remake? Or create separate VM per bottom sheet or (better),
            // create a livedata to track their states, or (better) that gets triggered and closes sheets
            closeBottomSheet()
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the destination change listener to avoid memory leaks
        navController.removeOnDestinationChangedListener(destinationChangeListener)
    }

    private fun closeBottomSheet() {
        filesBottomSheet.closeBottomSheet()
    }

    inner class DirectoryAdapterCallbackImpl: DirectoryAdapterCallback<FileSystemElement> {
        override fun click(item: FileSystemElement, position: Int, view: View) {
            viewModel.onElementClicked(item)
        }

        override fun longClick(item: FileSystemElement, position: Int, view: View) {
            log("MyLog", "long click: ${item.name}")
            view.startFilesPrimaryActionMode("Hide file?", item.name, item, callback = fileActionModeCallback)
        }

        override fun actionClick(item: FileSystemElement, position: Int, view: View) {
            showFilesScreenPopupMenu(view, item, popupMenuActions)
        }
    }

    inner class PopupActions: FilesScreenPopupMenuActions {
        override fun edit(element: FileSystemElement) {
            log("MyLog","RR: ${element.name}")
            filesBottomSheet.showBottomSheet(FilesBottomSheetTypes.EDIT, childFragmentManager, element) { newName: String ->
                viewModel.editElement(element, newName)
            }
        }

        override fun copy(element: FileSystemElement) {

        }

        override fun cut(element: FileSystemElement) {

        }

        override fun moveToFolder(element: FileSystemElement) {

        }

        override fun compress(element: FileSystemElement) {

        }

        override fun delete(element: FileSystemElement) {
            filesBottomSheet.showBottomSheet(FilesBottomSheetTypes.DELETE, childFragmentManager, element) { name: String ->
                viewModel.deleteElement(element)
            }
        }

        override fun info(element: FileSystemElement) {

        }
    }

    inner class FilesOnActionModeItemClickCallbackImpl: FilesOnActionModeItemClickCallback {
        override fun hide(element: FileSystemElement) {
            log("MyLog", "hide: ${element.name}")
        }

//        override fun delete() {
//            log("MyLog", "delete")
//        }
    }
}