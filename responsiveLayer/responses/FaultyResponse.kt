package com.project.classtimetable.responsiveLayer.responses

import com.google.gson.annotations.SerializedName
import com.project.classtimetable.responsiveLayer.models.FacultyUnique

data class FaultyResponse (
   @SerializedName("error"   ) var error   : Boolean?        = null,
   @SerializedName("message" ) var message : String?         = null,
   @SerializedName("data"    ) var data    : ArrayList<FacultyUnique> = arrayListOf()
)