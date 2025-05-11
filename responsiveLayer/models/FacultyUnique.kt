package com.project.classtimetable.responsiveLayer.models

import com.google.gson.annotations.SerializedName

data class FacultyUnique (
   @SerializedName("sessionId"   ) var sessionId   : String? = null,
   @SerializedName("headContent" ) var headContent : String? = null
)
