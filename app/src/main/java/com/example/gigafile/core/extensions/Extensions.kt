package com.example.gigafile.core.extensions

import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.io.File
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

// TODO: Change
fun File.generateTempId() = this.absolutePath + this.extension + this.toURI().toString()