package com.project.classtimetable.presentationLayer.admin.viewPurpose

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.isVisible
import com.project.classtimetable.R
import com.project.classtimetable.databinding.ActivityChooseClassBinding
import com.project.classtimetable.presentationLayer.commonPack.CommonFun
import com.project.classtimetable.presentationLayer.commonPack.showToast
import com.project.classtimetable.responsiveLayer.RetroFit
import com.project.classtimetable.responsiveLayer.models.Group
import com.project.classtimetable.responsiveLayer.models.Years
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ChooseClass : AppCompatActivity() {
   private val bind by lazy {
      ActivityChooseClassBinding.inflate(layoutInflater)
   }

   private val back by lazy {
      BackView(this)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(bind.root)
      back.bind = bind
      back.bind.layout.isVisible=false
      back.p.show()
      CoroutineScope(IO).async {
         async {
            try {
               RetroFit.api.getGroup(condition = "getGroups")
            } catch (e: Exception) {
               withContext(Main) {
                  back.p.dismiss()
                  showToast(e.message)
               }
               null
            }
         }.await().let { it ->
            it?.body()?.data?.let { it1 ->
               withContext(Main) {
                  back.bind.layout.isVisible=true
                  bind.textView5.isVisible = it1.isNotEmpty()
                  bind.groupLin.isVisible = it1.isNotEmpty()
                  val array = ArrayList<String>()
                  it1.forEach {
                     it.groups?.let { array.add(it) }
                  }
                  bind.groups.adapter = ArrayAdapter(
                     this@ChooseClass, android.R.layout.simple_dropdown_item_1line, array
                  )
                  bind.groups.onItemSelectedListener = object : OnItemSelectedListener {
                     override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long,
                     ) {
                        it1[position].let {
                           back.startOurFlow(it)
                        }
                     }
                     override fun onNothingSelected(parent: AdapterView<*>?) {

                     }
                  }
                  back.p.dismiss()

               }


            }
         }
      }.start()
      bind.submit.setOnClickListener {

         back.setToPrepare()



      }
   }

}
