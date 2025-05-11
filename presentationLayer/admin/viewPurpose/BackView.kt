package com.project.classtimetable.presentationLayer.admin.viewPurpose

import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.project.classtimetable.databinding.ActivityChooseClassBinding
import com.project.classtimetable.responsiveLayer.models.Faculties
import com.project.classtimetable.presentationLayer.admin.NextLevel
import com.project.classtimetable.presentationLayer.commonPack.CommonFun
import com.project.classtimetable.presentationLayer.commonPack.showToast
import com.project.classtimetable.presentationLayer.models.TimeTableView
import com.project.classtimetable.responsiveLayer.RetroFit
import com.project.classtimetable.responsiveLayer.models.Group
import com.project.classtimetable.responsiveLayer.models.Sections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BackView(private val context: Context) {


   lateinit var bind: ActivityChooseClassBinding
   val p by lazy {
      CommonFun(context).p
   }

   fun startOurFlow(group: Group) {
      bind.year12Lin.isVisible = false
      bind.anotherSegment.isVisible = false
      p.show()
      CoroutineScope(IO).async {
         async {
            try {
               RetroFit.api.getYears(condition = "getYears", text = group.groups ?: "Nothing")
            } catch (e: Exception) {
               withContext(Main) {
                  Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                  p.dismiss()
               }
               null
            }
         }.await().let {
            withContext(Main) {
               it?.body()?.data?.let { it ->
                  val string = ArrayList<String>()
                  it.forEach { it1 ->
                     it1.year?.let {
                        string.add(it)
                     }
                  }
                  bind.year12Lin.isVisible = string.isNotEmpty()
                  bind.years12.adapter =
                     ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, string)
                  bind.anotherSegment.isVisible = string.isNotEmpty()
               }
               p.dismiss()
            }
         }
      }.start()
   }


   fun setToPrepare() {
      val topHeadContent = bind.topHeadContent.text.toString().trim()
     if (topHeadContent.isEmpty()) {
         context.showToast("Please enter you top head Content")
      } else {
         p.show()
         CoroutineScope(IO).async {
            async {
               try {
                  RetroFit.api.getSections(
                     condition = "getReasonsOfTime",
                     year = "${bind.years12.selectedItem}",
                     group = "${bind.groups.selectedItem}"
                  )
               } catch (e: Exception) {
                  withContext(Main) {
                     p.dismiss()
                     context.showToast(e.message)
                  }
                  null
               }
            }.await().let {
               withContext(Main) {
                  p.dismiss()
                  val body = it?.body()
                  body?.let {
                     runMyPlace(it.Faculties, it.Sections ,topHeadContent)
                  }
               }
            }
         }.start()
      }

   }

   private fun runMyPlace(
      it: ArrayList<Faculties>,
      sections: ArrayList<Sections>,
      topHeadContent: String,
   ) {
      try {
         if (sections.isEmpty()) {
            context.showToast("No Sections were")
         } else {
            context.showToast(it.size)
            if (it.size >= 10) {
               val time = ArrayList<TimeTableView>()
               val array=ArrayList<String>()
               sections.forEachIndexed { ind, section ->
                  if (section.section != null) {
                     array.add(section.section!!)
                     val days = arrayOf("MON", "TUE", "WED", "THU", "FRI", "SAT")
                     for (item in days) {
                        val shuffled = (1..<it.size).shuffled()
                        for (count in 1..7) {

                           val faculty= it[shuffled[(1..<it.size-1).random()]]

                           val find = time.find { it.day == item && it.first == faculty && it.periods == "$count"&&it.section=="${section.section}" }
                           if(find==null){
                              time.add(TimeTableView(
                                 day = item, periods = "$count", first =faculty,
                                 section = "${section.section}"
                              ))
                           }else{
                           time.add(TimeTableView(
                              day = item, periods = "$count", first = it[shuffled[1]],
                              section = "${section.section}"
                           ))
                           }
                        }
                     }


                  }
               }
               context.startActivity(Intent(context,NextLevel::class.java)
                  .putExtra("data",time)
                  .putExtra("sections",array)
                  .putExtra("years","${bind.years12.selectedItem}")
                  .putExtra("groups","${bind.groups.selectedItem}")
                  .putExtra("topHeadContent",topHeadContent)
               )
            } else {
               context.showToast("Faculties are two Lower For this")
            }
         }

      } catch (e: Exception) {
         context.showToast("Sorry Something went wrong ${e.message}")
      }
   }
}