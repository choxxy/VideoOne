package com.example.videoone.model


data class Videos(
    val videos: List<Video>

)

data class Video(
      val mediaId: Long
    , val sourceUrl: String
    , val title: String
    , val genre: String
    , val duration: String
    , val folder: String
    , val thumbnail: String
)