package com.example.gigafile.presentation.screens.files_screen

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.gigafile.core.bases.BaseBottomSheet
import com.example.gigafile.core.bases.BaseScreen
import com.example.gigafile.databinding.FragmentFilesCreateBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilesScreenCreateBottomSheet: BottomSheetDialogFragment(), BaseScreen, BaseBottomSheet {
    private lateinit var binding: FragmentFilesCreateBottomSheetBinding
    private lateinit var createAction: (name: String) -> Unit
    private lateinit var element: Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilesCreateBottomSheetBinding.inflate(inflater, container, false)
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
        binding.createButton.setOnClickListener {
            createAction(binding.textField.text.toString())
        }
    }

    override fun initObservers() {}

    override fun onResume() {
        super.onResume()
        binding.textFieldLayout.editText?.text?.clear()
    }

    override fun <T, K> showBottomSheet(
        fragmentManager: FragmentManager,
        argument: T,
        action: (argument: K) -> Unit
    ) {
        createAction = action as (String) -> Unit
        element = argument as Unit
        show(fragmentManager, tag)
    }

    override fun closeBottomSheet() {
        dismissNow()
    }
}