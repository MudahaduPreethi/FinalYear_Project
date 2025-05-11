package com.project.classtimetable.presentationLayer.usersView

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.project.classtimetable.dataLayer.AdapterViewTime
import com.project.classtimetable.dataLayer.TimeAdapter
import com.project.classtimetable.databinding.ActivityViewMainBinding
import com.project.classtimetable.presentationLayer.commonPack.MainActivity
import com.project.classtimetable.presentationLayer.commonPack.showToast
import com.project.classtimetable.responsiveLayer.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ViewMainActivity : AppCompatActivity() {
   private val bind by lazy {
      ActivityViewMainBinding.inflate(layoutInflater)
   }

   private val dialog by lazy {
      MaterialAlertDialogBuilder(this).apply {
         setTitle("Do you want to logout ?")
         setPositiveButton("Yes") { p, _ ->
            getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
            finishAffinity()
            startActivity(Intent(this@ViewMainActivity, MainActivity::class.java))
            p.dismiss()
         }
         setNegativeButton("No") { l, _ ->
            l.dismiss()
         }
      }
   }


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(bind.root)

      bind.logout2.setOnClickListener {
         dialog()
      }

      getSharedPreferences("user", MODE_PRIVATE).let { it ->
         bind.wising.text = HtmlCompat.fromHtml(
            "Hi ${it.getString("name", "")} \uD83D\uDE42 !!",
            HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
         )
         bind.dashboard.text = stateMyLogic(it)

         CoroutineScope(IO).async {
            if (it.getString("type", "") == "Faculty") {
               async {
                  try {
                     RetroFit.api.getFacultyTimeTable(
                        id = "${it.getString("id", "")}",
                        condition = "getFacultyTimeTable"
                     )
                  } catch (e: Exception) {
                     withContext(Main) {
                        showToast(e.message)
                     }
                     null
                  }
               }.await().let {
                  it?.body()?.data?.let {
                     withContext(Main) {
                        bind.cycle.adapter = TimeAdapter(this@ViewMainActivity, it)
                     }
                  }
               }
            } else {
               async {
                  try {
                     RetroFit.api.getMyTables(
                        condition = "getTimeTables",
                        group = "${it.getString("groups", "")}",
                        years = "${it.getString("year", "")}"
                     )
                  } catch (e: Exception) {
                     withContext(Main) {
                        showToast(e.message)
                     }
                     null
                  }
               }.await().let {
                  it?.body()?.data?.let {
                     withContext(Main) {
                        bind.cycle.adapter = AdapterViewTime(this@ViewMainActivity, it)
                     }
                  }
               }
            }


         }.start()
      }


   }

   private fun dialog() {
      dialog.show()
   }

   private fun stateMyLogic(it: SharedPreferences?): Spanned {
      val string =
         when (it?.getString("type", "")) {
            "Faculty" -> {
               "<b>Mail-ID :</b> ${it.getString("mail", "")}" +
                       "<br><b>Year :</b>${it.getString("year", "")}" +
                       "<br><b>Group :</b>${it.getString("groups", "")}" +
                       "<br><b>Section :</b>${it.getString("section", "")}" +
                       "<br><b>Mobile :</b>${it.getString("mobile", "")}"
            }

            "Student" -> {
               "<b>Mail-ID :</b> ${it.getString("mail", "")}" +
                       "<br><b>Year :</b>${it.getString("year", "")}" +
                       "<br><b>Group :</b>${it.getString("groups", "")}" +
                       "<br><b>Subject :</b>${it.getString("section", "")}" +
                       "<br><b>Mobile :</b>${it.getString("mobile", "")}"
            };else -> {
            ""
         }
         }
      return HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)

   }
}