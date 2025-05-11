package com.project.classtimetable.presentationLayer.commonPack

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.text.HtmlCompat

fun Activity.showToast(message: Any?) = Toast.makeText(
   this, "$message", Toast.LENGTH_SHORT).show()

fun Context.showToast(message: Any?)=Toast.makeText(
   this, "$message", Toast.LENGTH_SHORT).show()
fun logData(message: Any?)= Log.i("TestPointOfView","$message")
fun spanned(message: String)=HtmlCompat.fromHtml(message,
   HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
)