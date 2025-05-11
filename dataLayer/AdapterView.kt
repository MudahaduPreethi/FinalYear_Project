package com.project.classtimetable.dataLayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.classtimetable.databinding.TableFragBinding
import com.project.classtimetable.presentationLayer.commonPack.spanned
import com.project.classtimetable.responsiveLayer.models.LoginClass

class AdapterView(val context: Context, val array: ArrayList<LoginClass>) :
   RecyclerView.Adapter<AdapterView.ViewPoint>() {
   class ViewPoint(val item: TableFragBinding) : RecyclerView.ViewHolder(item.root)

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewPoint(
      TableFragBinding.inflate(LayoutInflater.from(context), parent, false)
   )

   override fun getItemCount() = array.size

   override fun onBindViewHolder(holder: ViewPoint, position: Int) {
      with(holder.item) {
         table.isVisible = false
         textView6.width = LinearLayout.LayoutParams.MATCH_PARENT

         array[position].let {
            val k=when (it.type) {
               "Faculty" -> {
                  "<b>Mail-ID :</b> ${it.mail}" +
                          "<br><b>Year :</b>${it.year}" +
                          "<br><b>Group :</b>${it.groups}" +
                          "<br><b>Section :</b>${it.section}" +
                          "<br><b>Mobile :</b>${it.mobile}"
               }

               "Student" -> {
                  "<b>Mail-ID :</b> ${it.mail}" +
                          "<br><b>Year :</b>${it.year}" +
                          "<br><b>Group :</b>${it.groups}" +
                          "<br><b>Subject :</b>${it.section}" +
                          "<br><b>Mobile :</b>${it.mobile}"
               }

               else -> {""}
            }
            textView6.text = spanned(k)
         }

      }
   }
}