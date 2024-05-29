package com.example.gigafile.presentation.screens.files_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.gigafile.core.bases.BaseBottomSheet
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.databinding.FragmentFilesDeleteBottomSheetBinding
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilesScreenDeleteBottomSheet: BottomSheetDialogFragment(), BaseScreen, BaseBottomSheet {
    private lateinit var binding: FragmentFilesDeleteBottomSheetBinding
    private lateinit var deleteAction: (name: String) -> Unit
    private lateinit var element: FileSystemElement

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilesDeleteBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun initViews() {
        binding.elementName.text = element.name
        binding.cancelButton.setOnClickListener {
            closeBottomSheet()
        }
        binding.deleteButton.setOnClickListener {
            deleteAction(element.name)
        }
    }

    override fun initObservers() {}

    override fun <T, K> showBottomSheet(
        fragmentManager: FragmentManager,
        argument: T,
        action: (argument: K) -> Unit,
    ) {
        deleteAction = action as (String) -> Unit
        element = argument as FileSystemElement
        show(fragmentManager, tag)
    }

    override fun closeBottomSheet() {
        dismissNow()
    }
}