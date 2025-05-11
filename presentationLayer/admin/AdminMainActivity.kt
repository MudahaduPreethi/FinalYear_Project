package com.project.classtimetable.presentationLayer.admin

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.classtimetable.R
import com.project.classtimetable.dataLayer.AdapterView
import com.project.classtimetable.databinding.ActivityViewPurposeBinding
import com.project.classtimetable.presentationLayer.admin.viewPurpose.ChooseClass
import com.project.classtimetable.presentationLayer.commonPack.MainActivity
import com.project.classtimetable.presentationLayer.commonPack.showToast
import com.project.classtimetable.responsiveLayer.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AdminMainActivity : AppCompatActivity() {
   private val bind by lazy {
      ActivityViewPurposeBinding.inflate(layoutInflater)
   }
   private val dialog by lazy {
      MaterialAlertDialogBuilder(this).apply {
         setTitle("Do you want to logout ?")
         setPositiveButton("Yes") { p, _ ->
            getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
            finishAffinity()
            startActivity(Intent(this@AdminMainActivity, MainActivity::class.java))
            p.dismiss()
         }
         setNegativeButton("No") { l, _ ->
            l.dismiss()
         }

      }
   }
   private val viewBinding by lazy {
      Dialog(this).apply {
         setContentView(R.layout.cycle_view)
         window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         setOnShowListener {
            getData()
         }
      }
   }
   private fun getData() {
      CoroutineScope(IO).async {
         async {
            try {
               RetroFit.api.getUsers(condition = "getUsers")
            } catch (e: Exception) {
               null
            }
         }.await().let {
            it?.body()?.data?.let {
               withContext(Main) {
                  val cycle = viewBinding.findViewById<RecyclerView>(R.id.cycle2)
                  cycle.adapter = AdapterView(this@AdminMainActivity, it)
               }
            }
         }
      }.start()
   }


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(bind.root)
      bind.createTimeTable.setOnClickListener {
         startActivity(Intent(this, ChooseClass::class.java))
      }

      bind.logout.setOnClickListener {
         dialog()
      }

      bind.users.setOnClickListener {
         viewBinding.show()
      }

   }

   private fun dialog() {
      dialog.show()
   }
}