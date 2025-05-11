package com.project.classtimetable.presentationLayer.usersView

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.project.classtimetable.R

class ImageLoader : AppCompatActivity() {

   @SuppressLint("MissingInflatedId")
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_image_loader)
      findViewById<ImageView>(R.id.imagePortion).load(intent.getStringExtra("myURl"))
   }
}