package com.project.classtimetable.responsiveLayer.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Faculties (

   @SerializedName("id"       ) var id       : String? = null,
   @SerializedName("name"     ) var name     : String? = null,
   @SerializedName("mobile"   ) var mobile   : String? = null,
   @SerializedName("mail"     ) var mail     : String? = null,
   @SerializedName("year"     ) var year     : String? = null,
   @SerializedName("section"  ) var section  : String? = null,
   @SerializedName("type"     ) var type     : String? = null,
   @SerializedName("groups"   ) var groups   : String? = null
):Parcelable {
   constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString()
   ) {
   }

   override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeString(id)
      parcel.writeString(name)
      parcel.writeString(mobile)
      parcel.writeString(mail)
      parcel.writeString(year)
      parcel.writeString(section)
      parcel.writeString(type)
      parcel.writeString(groups)
   }

   override fun describeContents(): Int {
      return 0
   }

   companion object CREATOR : Parcelable.Creator<Faculties> {
      override fun createFromParcel(parcel: Parcel): Faculties {
         return Faculties(parcel)
      }

      override fun newArray(size: Int): Array<Faculties?> {
         return arrayOfNulls(size)
      }
   }
}
