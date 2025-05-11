package com.project.classtimetable.responsiveLayer.responses

import com.google.gson.annotations.SerializedName
import com.project.classtimetable.responsiveLayer.models.Faculties
import com.project.classtimetable.responsiveLayer.models.Sections

data class TimeResponses (
   @SerializedName("error"     ) var error     : String?              = null,
   @SerializedName("Faculties" ) var Faculties : ArrayList<Faculties> = arrayListOf(),
   @SerializedName("Sections"  ) var Sections  : ArrayList<Sections>  = arrayListOf()
)
