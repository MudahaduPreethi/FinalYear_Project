package com.project.classtimetable.dataLayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.project.classtimetable.databinding.TableFragBinding
import com.project.classtimetable.presentationLayer.commonPack.spanned
import com.project.classtimetable.presentationLayer.usersView.ImageLoader
import com.project.classtimetable.responsiveLayer.models.TimePositioning

class AdapterViewTime(val context: Context, val array: ArrayList<TimePositioning>) :
   RecyclerView.Adapter<AdapterViewTime.ViewPoint>() {
   class ViewPoint(
      val table: TableFragBinding,
   ) : RecyclerView.ViewHolder(table.root)

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewPoint(
      TableFragBinding.inflate(LayoutInflater.from(context), parent, false)
   )

   override fun getItemCount() = array.size

   override fun onBindViewHolder(holder: ViewPoint, position: Int) {
      with(holder.table) {
         array[position].let {
            textView6.text=spanned("${it.dateView}")
            table.setOnClickListener { _->
               context.startActivity(Intent(context, ImageLoader::class.java).putExtra("myURl",it.tablePath))
            }
         }


      }
   }
}