package com.example.videoone.network


import com.example.videoone.model.Video
import retrofit2.Response
import retrofit2.http.GET


interface VideoApi{

    @GET("/vidoes")
    suspend  fun getVideos() : Response<List<Video>>
}