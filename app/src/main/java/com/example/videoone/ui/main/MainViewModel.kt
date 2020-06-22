package com.example.videoone.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.videoone.model.Video
import com.example.videoone.model.VideoRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainViewModelModule =  module {
    viewModel { MainViewModel(get()) }
}

class MainViewModel(private val videoRepository: VideoRepository) : ViewModel() {

    val videoList:  LiveData<List<Video>> = liveData{
           val list = videoRepository.getVideos()
           emit(list.videos)
    }
}