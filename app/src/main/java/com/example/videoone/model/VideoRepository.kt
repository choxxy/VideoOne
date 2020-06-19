package com.example.videoone.model

import com.example.videoone.network.VideoApiService
import org.koin.dsl.module
import retrofit2.Response

val videoRepositoryModule  = module {
    factory { VideoRepository(get()) }
}

class VideoRepository(private val videoApiService: VideoApiService){

    suspend fun getVideos() : Response<List<Video>> {
        return  videoApiService.getApi().getVideos()
    }
}








