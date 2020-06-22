package com.example.videoone.model

import com.example.videoone.network.VideoApiService
import org.koin.dsl.module

val videoRepositoryModule  = module {
    factory { VideoRepository(get()) }
}

/** simple data manager **/
class VideoRepository(private val videoApiService: VideoApiService){
    suspend fun getVideos() : Videos  = videoApiService.getApi().getVideos()
}








