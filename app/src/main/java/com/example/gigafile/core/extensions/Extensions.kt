package com.example.gigafile.core.extensions

import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileLock
import java.nio.channels.OverlappingFileLockException
import java.util.Base64
import java.util.UUID

fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isLink(): Boolean = Patterns.WEB_URL.matcher(this).matches()

fun String.toBase64(): String = Base64.getEncoder().encodeToString(this.toByteArray())

fun String.fromBase64(): String = String(Base64.getDecoder().decode(this))

fun currentTime(): Long = System.currentTimeMillis()

fun randomId(): String = UUID.randomUUID().toString().replace("-", "").uppercase()

fun randomId(value: String): String = UUID.fromString(value).toString().replace("-", "").uppercase()

fun pathFromList(path: ArrayList<String>): String {
    return path.reduce { acc, s -> "$acc/$s" }
}

fun log(tag: String, message: Any){
    Log.d(tag, "Log: $message")
}

//fun logD(message: Any) {
//    Log.d(BuildConfig.DEBUG_MAIN, "$message")
//}
//
//fun logN(message: Any) {
//    Log.d(BuildConfig.DEBUG_NOTIFICATION, "$message")
//}

fun updateText(textView: TextView, message: Any) {
    textView.text = message.toString()
}

fun View.show(){
    if(visibility != View.VISIBLE) visibility = View.VISIBLE
}

fun View.hide(){
    if(visibility != View.GONE) visibility = View.GONE
}

fun View.showViews(vararg views: View){
    for(view in views) view.show()
}

fun View.hideViews(vararg views: View){
    for(view in views) view.hide()
}

fun View.isVisible(): Boolean{
    return visibility == View.VISIBLE
}

fun View.enable(){
    isEnabled = true
    isClickable = true
}

fun View.disable(){
    isEnabled = false
    isClickable = false
}

fun View.toast(message: Any){
    Toast.makeText(this.context, message.toString(), Toast.LENGTH_SHORT).show()
}

fun View.snackBar(message: Any){
    Snackbar.make(this.context, this.findViewById(android.R.id.content), message.toString(), Snackbar.LENGTH_SHORT).show()
}

fun showPopupMenu(
    anchor: View?,
    menuRes: Int,
    listener: (menuItem: MenuItem) -> Boolean
) {
    if(anchor == null) return
    val popupMenu = PopupMenu(anchor.context, anchor)
    popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)
    popupMenu.setOnMenuItemClickListener(listener)

    popupMenu.setForceShowIcon(true)
    popupMenu.show()
}

fun File.isSystemFile() = !(canRead() || canWrite())

fun File.containsSystemFile(): Boolean {
    if(isDirectory) {
        walkTopDown().forEach { file ->
            // Check if file is system
            // If even one of its sub-files is system file, file is considered system as well
            if(file.isSystemFile()) return true
        }
    }
    return false
}

/**
 * Optimized version of File.containsSystemFile()
 *
 * Be cautious using, it must be used only with files that are directories
 */
fun File.directoryContainsSystemFile(): Boolean {
    return walkTopDown().maxDepth(1).any { file ->
        !(file.canRead() || file.canWrite())
    }
}

fun File.isFileInUse(): Boolean {
    var fis: FileInputStream? = null
    var fos: FileOutputStream? = null
    var lock: FileLock? = null

    return try {
        if (exists()) {
            // Attempt to lock the file for reading
            fis = FileInputStream(this)
            lock = fis.channel.tryLock(0L, Long.MAX_VALUE, true)
        } else {
            // Attempt to lock the file for writing
            fos = FileOutputStream(this, true)
            lock = fos.channel.tryLock()
        }
        lock == null
    } catch (e: OverlappingFileLockException) {
        // File is being used by another process
        true
    } catch (e: Exception) {
        // Other IO exceptions
        false
    } finally {
        lock?.release()
        fis?.close()
        fos?.close()
    }
}

// TODO: Change
fun File.generateTempId() = this.absolutePath + this.extension + this.toURI().toString()