package com.example.videoone.model


data class Videos(
    val videos: List<Video>

)
data class Video(val mediaId: Long
                 , val sourceUrl:String
                 , val videoName:String
                 , val genre:String)