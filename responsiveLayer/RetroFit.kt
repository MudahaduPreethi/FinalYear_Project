package com.project.classtimetable.responsiveLayer

import retrofit2.converter.gson.GsonConverterFactory

object RetroFit {
private const val Url="https://wizzie.online/ClassTimeTable/"
   val api:Api by lazy {
      retrofit2.Retrofit.Builder()
         .baseUrl(Url)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
         .create(Api::class.java)
   }


}