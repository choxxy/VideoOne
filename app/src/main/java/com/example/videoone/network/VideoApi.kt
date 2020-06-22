package com.example.videoone.network



import com.example.videoone.model.Videos
import retrofit2.http.GET


interface VideoApi{
    @GET("/videos")
    suspend  fun getVideos() : Videos
}