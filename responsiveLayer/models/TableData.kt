package com.project.classtimetable.responsiveLayer.models

import com.google.gson.annotations.SerializedName

data class TableData (
   @SerializedName("id"          ) var id          : String? = null,
   @SerializedName("facultyid"   ) var facultyid   : String? = null,
   @SerializedName("period"      ) var period      : String? = null,
   @SerializedName("sessionId"   ) var sessionId   : String? = null,
   @SerializedName("day"         ) var day         : String? = null,
   @SerializedName("headContent" ) var headContent : String? = null,
   @SerializedName("sections"    ) var sections    : String? = null
)
