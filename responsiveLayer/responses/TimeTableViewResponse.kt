package com.project.classtimetable.responsiveLayer.responses

import com.google.gson.annotations.SerializedName
import com.project.classtimetable.responsiveLayer.models.TimePositioning

data class TimeTableViewResponse (
   @SerializedName("error"   ) var error   : Boolean?        = null,
   @SerializedName("message" ) var message : String?         = null,
   @SerializedName("data"    ) var data    : ArrayList<TimePositioning> = arrayListOf()
)