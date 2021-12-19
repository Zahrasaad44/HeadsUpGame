package com.example.headsup

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("celebrities/")
    fun getCelebrities() : Call<ArrayList<CelebritiesItem>>

    @POST("celebrities/")
    fun addCelebrity(@Body userCelebrity: CelebritiesItem): Call<CelebritiesItem>

    @PUT("celebrities/{id}")
    fun updateCelebrity(@Path("id") id: Int, @Body userCelebrity: CelebritiesItem): Call<CelebritiesItem>

    @DELETE("celebrities/{id}")
    fun deleteCelebrity(@Path("id") id: Int): Call<Void>



    @GET("celebrities/{id}")  //To get the single celebrity for UpdateDeleteCelebrity activity
    fun getCelebrity(@Path("id") id: Int): Call<CelebritiesItem>
}