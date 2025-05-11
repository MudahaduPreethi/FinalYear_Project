package com.project.classtimetable.responsiveLayer.models

import com.google.gson.annotations.SerializedName

data class LoginClass (
   @SerializedName("id"       ) var id       : String? = null,
   @SerializedName("name"     ) var name     : String? = null,
   @SerializedName("mobile"   ) var mobile   : String? = null,
   @SerializedName("mail"     ) var mail     : String? = null,
   @SerializedName("year"     ) var year     : String? = null,
   @SerializedName("section"  ) var section  : String? = null,
   @SerializedName("type"     ) var type     : String? = null,
   @SerializedName("groups"   ) var groups   : String? = null
)
