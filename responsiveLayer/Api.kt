package com.project.classtimetable.responsiveLayer

import com.project.classtimetable.responsiveLayer.responses.LoginResponse
import com.project.classtimetable.responsiveLayer.responses.CommonResponse
import com.project.classtimetable.responsiveLayer.responses.FaultyResponse
import com.project.classtimetable.responsiveLayer.responses.GroupResponse
import com.project.classtimetable.responsiveLayer.responses.SessionResponse
import com.project.classtimetable.responsiveLayer.responses.TimeResponses
import com.project.classtimetable.responsiveLayer.responses.TimeTableViewResponse
import com.project.classtimetable.responsiveLayer.responses.YearsResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
   @FormUrlEncoded
   @POST("users.php")
   suspend fun createUser(
      @Field("name") name: String,
      @Field("mobile") mobile: String,
      @Field("mail") mail: String,
      @Field("password") password: String,
      @Field("year") year: String,
      @Field("section") section: String,
      @Field("type") type: String,
   ): Response<CommonResponse>

   @GET("getData.php")
   suspend fun getGroup(
      @Query("condition") condition: String,
   ): Response<GroupResponse>

   @GET("getData.php")
   suspend fun getYears(
   @Query("condition")condition:String,
   @Query("text")text:String
   ):Response<YearsResponse>

   @GET("getData.php")
   suspend fun getSections(
      @Query("condition")condition:String,
      @Query("year")year:String,
      @Query("group")group:String
   ): Response<TimeResponses>

   @FormUrlEncoded
   @POST("addTimeTable.php")
   suspend fun addTable(
      @Field("className")className:String,
      @Field("year")year:String,
      @Field("table")table:String,
      @Field("section")section:String,
      @Field("dateView")dateView:String
      ): Response<CommonResponse>

   @GET("getData.php")
   suspend fun login(
      @Query("condition")condition:String,
      @Query("mail")mail:String,
      @Query("password")password:String
   ):Response<LoginResponse>

@GET("getData.php")
   suspend fun getMyTables(
      @Query("condition")condition:String,
      @Query("group")group:String,
      @Query("year")years: String
   ): Response<TimeTableViewResponse>

   @GET("getData.php")
   suspend fun getUsers(
      @Query("condition")condition:String,
   ):Response<LoginResponse>


   @FormUrlEncoded
   @POST("addFacultyTime.php")
   suspend fun addRegister(
      @Field("facultyid")facultyid:String,
      @Field("period")period:String,
      @Field("sessionId")sessionId:String,
      @Field("day")day:String,
      @Field("headContent")headContent:String,
      @Field("sections")sections:String
   ):Response<CommonResponse>

   @GET("getData.php")
    suspend fun getFacultyTimeTable(
       @Query("id")id:String,
       @Query("condition")condition:String,
    ):Response<FaultyResponse>

    @GET("getData.php")
   suspend fun getSessionId(
      @Query("id")id:String,
      @Query("condition")condition:String,
      @Query("facultyId")facultyId:String,
   ):Response<SessionResponse>


}
