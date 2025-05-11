package com.project.classtimetable.responsiveLayer.responses

import com.google.gson.annotations.SerializedName

data class CommonResponse (
   @SerializedName("error"   ) var error   : Boolean?          = null,
   @SerializedName("message" ) var message : String?           = null,
)