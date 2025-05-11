package com.project.classtimetable.responsiveLayer

import com.google.gson.annotations.SerializedName
import com.project.classtimetable.responsiveLayer.models.Faculties

data class FacultyResponse (
   @SerializedName("error"   ) var error   : Boolean?        = null,
   @SerializedName("message" ) var message : String?         = null,
   @SerializedName("data"    ) var data    : ArrayList<Faculties> = arrayListOf()
 )
