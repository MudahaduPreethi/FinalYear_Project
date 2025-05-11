package com.project.classtimetable.presentationLayer.models

import android.os.Parcel
import android.os.Parcelable
import com.project.classtimetable.responsiveLayer.models.Faculties

data class TimeTableView(
   var day: String?,
   var periods: String?,
   var first: Faculties,
   var section: String?
):Parcelable {
   constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readValue(Faculties::class.java.classLoader)as Faculties,
      parcel.readString()
   ) {
   }

   override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeString(day)
      parcel.writeString(periods)
      parcel.writeValue(first)
      parcel.writeString(section)
   }

   override fun describeContents(): Int {
      return 0
   }

   companion object CREATOR : Parcelable.Creator<TimeTableView> {
      override fun createFromParcel(parcel: Parcel): TimeTableView {
         return TimeTableView(parcel)
      }

      override fun newArray(size: Int): Array<TimeTableView?> {
         return arrayOfNulls(size)
      }
   }
}