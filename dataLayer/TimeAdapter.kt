package com.project.classtimetable.dataLayer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.classtimetable.databinding.TableFragBinding
import com.project.classtimetable.presentationLayer.usersView.faculty.ViewPoint
import com.project.classtimetable.responsiveLayer.models.FacultyUnique
import java.text.SimpleDateFormat
import java.util.Date

class TimeAdapter(
   val context: Context, private val array: ArrayList<FacultyUnique>,
) : RecyclerView.Adapter<TimeAdapter.ViewPoint>() {

   @SuppressLint("SimpleDateFormat")
   val simple=SimpleDateFormat("dd MMMM yyyy hh:mm - a")
   class ViewPoint(
      val item: TableFragBinding,
   ) : RecyclerView.ViewHolder(item.root)

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewPoint(
      TableFragBinding.inflate(LayoutInflater.from(context), parent, false)
   )

   override fun getItemCount() = array.size

   override fun onBindViewHolder(holder: ViewPoint, position: Int) {
      with(holder.item) {
         array[position].let {i->
            i.sessionId?.let { it ->
               var string="<b>"+i.headContent+"</b><br>"
               it.toLongOrNull()?.let {
                  string+=simple.format(Date(it))
               }

               table.setOnClickListener {
                  context.startActivity(Intent(context,com.project.classtimetable.presentationLayer.usersView.faculty.ViewPoint::class.java)
                     .putExtra("sessionId",i.sessionId))
               }

               this.textView6.text=HtmlCompat.fromHtml(string, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
            }
         }
      }
   }
}