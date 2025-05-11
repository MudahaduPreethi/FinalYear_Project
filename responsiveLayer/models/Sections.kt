package com.project.classtimetable.responsiveLayer.models

import com.google.gson.annotations.SerializedName

data class Sections (
   @SerializedName("section" ) var section : String? = null
)
