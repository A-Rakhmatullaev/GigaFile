package com.example.gigafile.presentation.utils

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import com.example.gigafile.R
import com.example.gigafile.core.extensions.log
import com.example.gigafile.domain.models.core.file_system.FileSystemElement

class PrimaryActionModeCallback(
    @MenuRes private val menuResId: Int,
    private val title: String? = null,
    private val subtitle: String? = null,
    private val clickListener: OnActionModeItemClickListener
): ActionMode.Callback {
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        // Called after startActionMode
        // Inflate a menu resource providing context menu items
        if(mode == null || menu == null) return false
        //throw Exception("No ActionMode is provided!")
        mode.menuInflater?.inflate(menuResId, menu)
        mode.title = title
        mode.subtitle = subtitle
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        // Called each time the action mode is shown
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        // Called when the user selects a contextual menu item
        if(item == null) {
            mode?.finish()
            return false
        }
        clickListener.onActionItemClick(item)
        mode?.finish()
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        // Called when the action mode is finished
        log("MyLog","ActionMode is destroyed")
    }
}

interface OnActionModeItemClickListener {
    fun onActionItemClick(item: MenuItem)
}

// TODO: move items below to FilesScreen folder
fun View.startFilesPrimaryActionMode(
    title: String? = null,
    subtitle: String? = null,
    element: FileSystemElement,
    callback: FilesOnActionModeItemClickCallback
) {
    startActionMode(
        PrimaryActionModeCallback(
            R.menu.files_screen_action_mode_menu,
            title,
            subtitle,
            FilesOnActionModeItemClickListener(callback, element)
        )
    )
}

class FilesOnActionModeItemClickListener(
    private val callback: FilesOnActionModeItemClickCallback,
    private val element: FileSystemElement
): OnActionModeItemClickListener{
    override fun onActionItemClick(item: MenuItem) {
        when(item.itemId) {
            R.id.hide -> {
                callback.hide(element)
            }
        }
    }

}

interface FilesOnActionModeItemClickCallback {
    fun hide(element: FileSystemElement)
    //fun delete()
}