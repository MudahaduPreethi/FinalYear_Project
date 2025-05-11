package com.project.classtimetable.responsiveLayer.responses

import com.google.gson.annotations.SerializedName
import com.project.classtimetable.responsiveLayer.models.Group

data class GroupResponse (
   @SerializedName("error"   ) var error   : Boolean?        = null,
   @SerializedName("message" ) var message : String?         = null,
   @SerializedName("data"    ) var data    : ArrayList<Group> = arrayListOf()
)