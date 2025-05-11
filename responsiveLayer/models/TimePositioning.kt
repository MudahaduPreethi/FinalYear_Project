package com.project.classtimetable.responsiveLayer.models

import com.google.gson.annotations.SerializedName

data class TimePositioning (
   @SerializedName("id"        ) var id        : String? = null,
   @SerializedName("className" ) var className : String? = null,
   @SerializedName("year"      ) var year      : String? = null,
   @SerializedName("tablePath" ) var tablePath : String? = null,
   @SerializedName("section"   ) var section   : String? = null,
   @SerializedName("dateView"   ) var dateView   : String? = null,

)

