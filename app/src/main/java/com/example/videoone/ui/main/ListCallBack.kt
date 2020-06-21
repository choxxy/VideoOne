package com.example.videoone.ui.main

import com.example.videoone.model.Video

interface ListCallBack {
    fun onItemClick(video: Video)
}