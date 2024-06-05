package com.example.gigafile.presentation.screens.files_screen

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import com.example.gigafile.R
import com.example.gigafile.core.bases.BaseBottomSheet
import com.example.gigafile.core.extensions.log
import com.example.gigafile.core.extensions.showPopupMenu
import com.example.gigafile.domain.models.core.file_system.FileSystemElement

fun showFilesScreenPopupMenu(anchor: View?, element: FileSystemElement, actions: FilesScreenPopupMenuActions) {
    val popupAction:(menuItem: MenuItem) -> Boolean = {
        when(it.itemId) {
            R.id.edit -> {
                log("MyLog", "Menu: ${element.name}")
                actions.edit(element)
            }

            R.id.copy -> {
                actions.copy(element)
            }

            R.id.cut -> {
                actions.cut(element)
            }

            R.id.move_to_folder -> {
                actions.moveToFolder(element)
            }

            R.id.compress -> {
                actions.compress(element)
            }

            R.id.delete -> {
                actions.delete(element)
            }

            R.id.info -> {
                actions.info(element)
            }
        }
        true
    }

    showPopupMenu(anchor, R.menu.files_screen_popup_menu, popupAction)
}

interface FilesScreenPopupMenuActions {
    fun edit(element: FileSystemElement)
    fun copy(element: FileSystemElement)
    fun cut(element: FileSystemElement)
    fun moveToFolder(element: FileSystemElement)
    fun compress(element: FileSystemElement)
    fun delete(element: FileSystemElement)
    fun info(element: FileSystemElement)
}

enum class FilesBottomSheetTypes {
    CREATE,
    EDIT,
    DELETE
}

interface FilesBottomSheet: BaseBottomSheet {
    fun <T, K> showBottomSheet(
        type: FilesBottomSheetTypes,
        fragmentManager: FragmentManager,
        argument: T,
        action: (argument: K) -> Unit
    )
}

// TODO: Remake! I'm sure there was a pattern for this use case, Builder or Factory, or similar
// TODO: Remake!!!
class FilesBottomSheetImpl: FilesBottomSheet {
    private val bottomSheetCreateDialogFragment = FilesScreenCreateBottomSheet()
    private val bottomSheetEditDialogFragment = FilesScreenEditBottomSheet()
    private val bottomSheetDeleteDialogFragment = FilesScreenDeleteBottomSheet()

    override fun <T, K> showBottomSheet(
        type: FilesBottomSheetTypes,
        fragmentManager: FragmentManager,
        argument: T,
        action: (argument: K) -> Unit
    ) {
        when(type) {
            FilesBottomSheetTypes.CREATE -> {
                bottomSheetCreateDialogFragment.showBottomSheet(fragmentManager, argument, action)
            }
            FilesBottomSheetTypes.EDIT -> {
                bottomSheetEditDialogFragment.showBottomSheet(fragmentManager, argument, action)
            }
            FilesBottomSheetTypes.DELETE -> {
                bottomSheetDeleteDialogFragment.showBottomSheet(fragmentManager, argument, action)
            }
        }
    }

    override fun <T, K> showBottomSheet(
        fragmentManager: FragmentManager,
        argument: T,
        action: (argument: K) -> Unit,
    ) {
        return
    }

    override fun closeBottomSheet() {
        if(bottomSheetCreateDialogFragment.isVisible) bottomSheetCreateDialogFragment.closeBottomSheet()
        if(bottomSheetEditDialogFragment.isVisible) bottomSheetEditDialogFragment.closeBottomSheet()
        if(bottomSheetDeleteDialogFragment.isVisible) bottomSheetDeleteDialogFragment.closeBottomSheet()
    }
}