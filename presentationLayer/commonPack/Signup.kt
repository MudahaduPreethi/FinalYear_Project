package com.project.classtimetable.presentationLayer.commonPack

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.project.classtimetable.databinding.ActivitySignupBinding
import com.project.classtimetable.responsiveLayer.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class Signup : AppCompatActivity() {
   private val bind by lazy {
      ActivitySignupBinding.inflate(layoutInflater)
   }
   private val p by lazy {
      CommonFun(this).p
   }


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContentView(bind.root)


      bind.signIn.setOnClickListener {
         finish()
      }

      bind.name.setOnFocusChangeListener { _, hasFocus ->
         setViewPoint(hasFocus, bind.nameView)
      }

      bind.name.requestFocus()
      bind.name.isFocusable = true
      window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

      bind.password2.setOnFocusChangeListener { _, hasFocus ->
         setViewPoint(hasFocus, bind.passView2)
      }
      bind.mobile.setOnFocusChangeListener { _, hasFocus ->
         setViewPoint(hasFocus, bind.mobileView)
      }
      bind.email2.setOnFocusChangeListener { _, focus ->
         setViewPoint(hasFocus = focus, nameView = bind.emailView2)
      }
      bind.section.setOnFocusChangeListener { _, p ->
         setViewPoint(hasFocus = p, nameView = bind.sectionView)
      }
      bind.year.setOnFocusChangeListener { _, hasFocus ->
         setViewPoint(hasFocus = hasFocus, nameView = bind.yearView)
      }
      val types = arrayOf("Student", "Faculty")
      bind.spinner.adapter = ArrayAdapter(
         this, android.R.layout.simple_dropdown_item_1line, types
      )
      bind.group.adapter = ArrayAdapter(
         this,
         android.R.layout.simple_dropdown_item_1line,
         arrayOf("Groups", "CSE", "ECE", "ME", "EEE", "CE", "IT")
      )

      bind.spinner.onItemSelectedListener = object : OnItemSelectedListener {
         override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long, ) {
            bind.section.hint = if (position == 0) {
               "Section"
            } else {
               "Subject"
            }
         }

         override fun onNothingSelected(parent: AdapterView<*>?) {
         }
      }

      onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
         override fun handleOnBackPressed() {
            finish()
         }
      })




      bind.appCompatButton2.setOnClickListener {
         val name = bind.name.text.toString()
         val email = bind.email2.text.toString()
         val password = bind.password2.text.toString()
         val mobile = bind.mobile.text.toString()
         val section = bind.section.text.toString()
         val year = bind.year.text.toString()
         val type = bind.spinner.selectedItem.toString()
         val group = bind.group.selectedItem.toString()

         if (name.isEmpty()) {
            showToast("Please enter your name")
         } else if (email.isEmpty()) {
            showToast("Please enter your email")
         } else if (!email.contains("@gmail.com")) {
            showToast("Please enter a valid email")
         } else if (password.isEmpty()) {
            showToast("Please enter your password")
         } else if (mobile.isEmpty()) {
            showToast("Please enter your mobile")
         } else if (section.isEmpty()) {
            showToast("Please enter your section")
         } else if (year.isEmpty()) {
            showToast("Please enter the year")
         } else if (group == "Group") {
            showToast("Please enter select group")
         } else {
            p.show()
            CoroutineScope(IO).async {
               async {
                  try {
                     RetroFit.api.createUser(
                        name = name,
                        mobile = mobile,
                        mail = email,
                        password = password,
                        year = year,
                        section = section,
                        type = type
                     )

                  } catch (e: Exception) {
                     withContext(Main) {
                        showToast(e.message)
                        p.dismiss()
                     }
                     null
                  }
               }.await().let {
                  withContext(Main) {
                     it?.body()?.message?.let {
                        if (it == "Success") {
                           finish()
                        }
                        showToast(it)
                     }
                     p.dismiss()
                  }
               }
            }.start()


         }

      }

   }

   private fun setViewPoint(hasFocus: Boolean, nameView: View) {
      if (hasFocus) {
         nameView.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
      } else {
         nameView.backgroundTintList = null
      }
   }
}
