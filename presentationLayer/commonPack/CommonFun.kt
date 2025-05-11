package com.project.classtimetable.presentationLayer.commonPack

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.project.classtimetable.R

class CommonFun(private val context: Context) {
   val p by lazy {
      Dialog(context).apply {
         setCancelable(false)
         setContentView(R.layout.progress)
         window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      }
   }
}