package com.project.classtimetable.presentationLayer.usersView.faculty

import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.core.view.isVisible
import com.project.classtimetable.databinding.ActivityNextLevelBinding
import com.project.classtimetable.presentationLayer.commonPack.CommonFun
import com.project.classtimetable.presentationLayer.commonPack.logData
import com.project.classtimetable.presentationLayer.commonPack.showToast
import com.project.classtimetable.responsiveLayer.RetroFit
import com.project.classtimetable.responsiveLayer.models.TableData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ViewPoint : AppCompatActivity() {
   private val bind by lazy {
      ActivityNextLevelBinding.inflate(layoutInflater)
   }
   private val p by lazy {
      CommonFun(this).p
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(bind.root)
      bind.spinner2.isVisible = false

      setterPoint(bind.mon)
      setterPoint(bind.tue)
      setterPoint(bind.wed)
      setterPoint(bind.thu)
      setterPoint(bind.fri)
      setterPoint(bind.sat)


      p.show()

      val id = getSharedPreferences("user", MODE_PRIVATE).getString("id", "")
      CoroutineScope(IO).async {
         async {
            try {
               RetroFit.api.getSessionId(
                  id = "${intent.getStringExtra("sessionId")}",
                  condition = "getFacultyId",
                  facultyId = "$id"
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
               it?.body()?.data?.let {
                  setMyTimeTable(it)
               }
               p.dismiss()
            }
         }
      }.start()
   }

   private fun setterPoint(mon: TableRow) {
      mon.forEachIndexed { index, it ->
         if(index!=0 && index!=7&&index!=4){
            if(it is TextView){ it.text = "" }
         }

      }

   }

   private fun setMyTimeTable(tableData: ArrayList<TableData>) {
      if (tableData.isNotEmpty()) {
         bind.headContent.text = tableData[0].headContent
      }
      val groupBy = tableData.groupBy { it.day }
      for (key in groupBy.keys) {
         if (key != null) {
            val table: TableRow? = when (key) {
               "MON" -> {
                  bind.mon
               }

               "TUE" -> {
                  bind.tue
               }

               "WED" -> {
                  bind.wed
               }

               "THU" -> {
                  bind.thu
               }

               "FRI" -> {
                  bind.fri
               }

               "SAT" -> {
                  bind.sat
               }

               else -> {
                  null
               }
            }

            (groupBy[key] ?: emptyList()).forEach { periods ->
               periods.period?.toIntOrNull()?.let { period ->
                  logData(period)
                  table?.let {
                     val num=if(period==4||period==7){ period+1 }else{ period }
                     val k = it[num]
                     if (k is TextView) {
                        k.text = periods.sections
                     }
                  }
               }
            }
         }
      }


   }
}