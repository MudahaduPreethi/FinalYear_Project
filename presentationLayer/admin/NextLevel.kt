package com.project.classtimetable.presentationLayer.admin

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BitmapCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.core.view.size
import com.project.classtimetable.R
import com.project.classtimetable.databinding.ActivityNextLevelBinding
import com.project.classtimetable.presentationLayer.commonPack.CommonFun
import com.project.classtimetable.presentationLayer.commonPack.logData
import com.project.classtimetable.presentationLayer.commonPack.showToast
import com.project.classtimetable.presentationLayer.models.TextViewModel
import com.project.classtimetable.presentationLayer.models.TimeTableView
import com.project.classtimetable.responsiveLayer.RetroFit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.log
import kotlin.math.max

class NextLevel : AppCompatActivity() {
   private val bind by lazy {
      ActivityNextLevelBinding.inflate(layoutInflater)
   }
   private val groups = mutableListOf<TextViewModel>()

   private val p by lazy {
      CommonFun(this).p
   }
   var text=""

   @SuppressLint("SimpleDateFormat")
   private val simple = SimpleDateFormat("dd MMMM yyyy hh:mm:ss a")
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(bind.root)

      val timeTableViews = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         intent.getParcelableArrayListExtra("data", TimeTableView::class.java)
      } else {
         intent.getParcelableArrayListExtra("data")
      }
      val sections = intent.getStringArrayListExtra("sections")
      if (timeTableViews != null && sections != null) {
         sections.forEach { it1 ->
            if (it1 != null) {
               val element = timeTableViews.filter { it.section == it1 }
               groups.add(TextViewModel(section = it1, array = element))
            }
         }


         bind.headContent.text = HtmlCompat.fromHtml(
            "${intent.getStringExtra("topHeadContent")}",
            HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
         )
         bind.send.setOnClickListener {
            changeBitmap().let {
               p.show()
               CoroutineScope(IO).async {
                  async {
                     val out = ByteArrayOutputStream()
                     it.compress(Bitmap.CompressFormat.PNG, 100, out)
                     try {
                        RetroFit.api.addTable(
                           className = "${intent.getStringExtra("groups")}",
                           year = "${intent.getStringExtra("years")}",
                           table = Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP),
                           section = "${bind.spinner2.selectedItem}",
                           dateView = simple.format(Date())
                        )
                     } catch (e: Exception) {
                        withContext(Main) {
                           showToast(e.message)
                           p.dismiss()
                        }
                        null
                     }
                  }.await()
                  val millis = System.currentTimeMillis()
                  var num = 0
                  for (item in timeTableViews) {
                     async {
                        try {
                           RetroFit.api.addRegister(
                              facultyid = "${item.first.id}",
                              period = "${item.periods}",
                              sessionId = "$millis",
                              day = "${item.day}",
                              headContent = "${bind.headContent.text}"
                              ,sections=text
                           )
                        } catch (e: Exception) {
                           num++
                           if(num==timeTableViews.size){
                              withContext(Main){
                                 p.dismiss()
                                 finish()
                              }
                           }
                           null
                        }
                     }.await().let {
                        num++
                        if(num==timeTableViews.size){
                           withContext(Main){
                              p.dismiss()
                              finish()
                           }
                        }
                     }
                  }

               }.start()
            }
         }

         bind.spinner2.adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sections)
         bind.spinner2.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
               parent: AdapterView<*>?,
               view: View?,
               position: Int,
               id: Long,
            ) {
               selected(sections[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
         }

      }
   }



   private fun changeBitmap(): Bitmap {
      var width = 0
      var height = 0

      for (i in 0 until bind.horizontalScrollView.childCount) {
         val child = bind.horizontalScrollView.getChildAt(i)
         width += child.width
         height += max(height, child.height)
      }

      val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
      val canvas = Canvas(bitmap)
      bind.linearLay.draw(canvas)
      return bitmap
   }

   @SuppressLint("SetTextI18n")
   private fun selected(string: String) {
      text=" ${intent.getStringExtra("groups")}-${intent.getStringExtra("years")}$string"
      bind.branch.text =
         "Section : $text"
      var filter: List<TimeTableView>? = null
      for (group in groups) {
         if (group.section == string) {
            filter = group.array
         }
      }


      val sortedWith = filter?.sortedWith(compareBy({ it.day }, { it.section }))
      val groupBy = sortedWith?.groupBy { it.day }
      showToast(filter?.size)

      val MON = groupBy?.get("MON") ?: emptyList()
      val TUE = groupBy?.get("TUE") ?: emptyList()
      val WED = groupBy?.get("WED") ?: emptyList()
      val THU = groupBy?.get("THU") ?: emptyList()
      val FRI = groupBy?.get("FRI") ?: emptyList()
      val SAT = groupBy?.get("SAT") ?: emptyList()


      MON.forEachIndexed { index, timeTableView ->
         setter(index = index + 1, timeTableView = timeTableView, mon = bind.mon)
      }
      TUE.forEachIndexed { index, timeTableView ->
         setter(index = index + 1, timeTableView = timeTableView, mon = bind.tue)
      }
      WED.forEachIndexed { index, timeTableView ->
         setter(index = index + 1, timeTableView = timeTableView, mon = bind.wed)
      }
      THU.forEachIndexed { index, timeTableView ->
         setter(index = index + 1, timeTableView = timeTableView, mon = bind.thu)
      }
      FRI.forEachIndexed { index, timeTableView ->
         setter(index = index + 1, timeTableView = timeTableView, mon = bind.fri)
      }
      SAT.forEachIndexed { index, timeTableView ->
         setter(index = index + 1, timeTableView = timeTableView, mon = bind.sat)
      }

   }

   private fun setter(index: Int, timeTableView: TimeTableView, mon: TableRow) {
      val i = if (index == 4 || index == 7) {
         mon[index + 1]
      } else {
         mon[index]
      }

      if (i is TextView) {
         i.text = "${timeTableView.first.section}"
      }
   }
}