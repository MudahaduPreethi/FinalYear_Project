package com.project.classtimetable.presentationLayer.commonPack

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.project.classtimetable.databinding.ActivityLoginBinding
import com.project.classtimetable.presentationLayer.admin.AdminMainActivity
import com.project.classtimetable.presentationLayer.usersView.ViewMainActivity
import com.project.classtimetable.responsiveLayer.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {

   private val bind by lazy {
      ActivityLoginBinding.inflate(layoutInflater)
   }
   private val p by lazy {
      CommonFun(this).p
   }

   @SuppressLint("SwitchIntDef")
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContentView(bind.root)


      bind.email.setOnFocusChangeListener { _, hasFocus ->
         setState(hasFocus, bind.emailView)
      }

      bind.email.requestFocus()
      bind.email.isFocusable = true
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

      bind.password.setOnFocusChangeListener { _, hasFocus ->
         setState(hasFocus, bind.passView)
      }
      bind.signUp.setOnClickListener {
         startActivity(Intent(this, Signup::class.java))
      }
      bind.appCompatButton.setOnClickListener {
         val email = bind.email.text.toString().trim()
         val password = bind.password.text.toString().trim()
         if (email.contains("admin@gmail.com") && password.contains("admin")) {
            getSharedPreferences("user", MODE_PRIVATE).edit().putString("type", "admin").apply()
            startActivity(Intent(this, AdminMainActivity::class.java))
         } else if (email.isEmpty()) {
            showToast("Please enter your email")
         } else if (password.isEmpty()) {
            showToast("Please enter your password")
         } else {
            p.show()
            CoroutineScope(IO).async {
               async {
                  try {
                     RetroFit.api.login(condition = "getLogin", mail = email, password = password)
                  } catch (e: Exception) {
                     withContext(Main) {
                        showToast(e.message)
                        p.dismiss()
                     }
                     null
                  }
               }.await().let {
                  withContext(Main) {
                     it?.body()?.data?.let {
                        if (it.isEmpty()) {
                           showToast("Invalid Credentials")
                        } else {
                           it[0].let {
                              getSharedPreferences("user", MODE_PRIVATE).edit().apply {
                                 putString("id", it.id)
                                 putString("name", it.name)
                                 putString("mobile", it.mobile)
                                 putString("mail", it.mail)
                                 putString("year", it.year)
                                 putString("section", it.section)
                                 putString("type", it.type)
                                 putString("groups", it.groups)
                                 apply()
                              }
                              finishAffinity()
                              startActivity(
                                 Intent(
                                    this@LoginActivity,
                                    ViewMainActivity::class.java
                                 )
                              )
                           }
                        }
                     }
                     p.dismiss()
                  }
               }
            }.start()
         }
      }


   }

   val dd = ""

   private fun setState(hasFocus: Boolean, emailView: View) {
      if (hasFocus) {
         emailView.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
      } else {
         emailView.backgroundTintList = null
      }
   }


}