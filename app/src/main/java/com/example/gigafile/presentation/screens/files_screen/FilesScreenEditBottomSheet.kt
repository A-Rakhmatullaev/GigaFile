package com.example.gigafile.presentation.screens.files_screen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.gigafile.core.bases.BaseBottomSheet
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.core.extensions.log
import com.example.gigafile.databinding.FragmentFilesEditBottomSheetBinding
import com.example.gigafile.domain.models.core.file_system.FileSystemElement
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilesScreenEditBottomSheet: BottomSheetDialogFragment(), BaseScreen, BaseBottomSheet {
    private lateinit var binding: FragmentFilesEditBottomSheetBinding
    private lateinit var editAction: (newName: String) -> Unit
    private lateinit var element: FileSystemElement

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilesEditBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun initViews() {
        binding.cancelButton.setOnClickListener {
            closeBottomSheet()
        }
        binding.editButton.setOnClickListener {
            editAction(binding.textField.text.toString())
        }
    }

    override fun initObservers() {}

    override fun onResume() {
        super.onResume()
        binding.textFieldLayout.editText?.setText(element.name)
    }

    override fun <T, K> showBottomSheet(
        fragmentManager: FragmentManager,
        argument: T,
        action: (argument: K) -> Unit
    ) {
        editAction = action as (String) -> Unit
        element = argument as FileSystemElement
        show(fragmentManager, tag)
    }

    override fun closeBottomSheet() {
        dismissNow()
    }
}