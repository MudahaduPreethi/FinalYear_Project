package com.project.classtimetable.presentationLayer.commonPack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.classtimetable.databinding.ActivityMainBinding
import com.project.classtimetable.presentationLayer.admin.AdminMainActivity
import com.project.classtimetable.presentationLayer.usersView.ViewMainActivity

class MainActivity : AppCompatActivity() {
   private val bind by lazy {
      ActivityMainBinding.inflate(layoutInflater)
   }
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(bind.root)
      with(bind.calendar) {
         alpha=0f

         animate().setStartDelay(1000).alpha(1f).withEndAction{
            finish()
            when(getSharedPreferences("user", MODE_PRIVATE).getString("type","")){
               "admin"-> {
                  startActivity(Intent(this@MainActivity, AdminMainActivity::class.java))
               }
               "Student","Faculty"->{
                  startActivity(Intent(this@MainActivity,ViewMainActivity::class.java))
               }
               else->{
                  startActivity(Intent(this@MainActivity, LoginActivity::class.java))
               }
            }
         }
      }


   }
}