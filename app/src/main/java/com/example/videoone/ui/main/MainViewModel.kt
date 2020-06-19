package com.example.videoone.ui.main

import androidx.lifecycle.ViewModel
import com.example.videoone.model.VideoRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainViewModelModule =  module {
    viewModel { MainViewModel(get()) }
}

class MainViewModel(val videoRepository: VideoRepository) : ViewModel() {

}