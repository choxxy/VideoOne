package com.example.videoone.network

import android.content.Context
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { VideoApiService(androidApplication()) }
}

class VideoApiService( private val context: Context) {


    fun getApi() :VideoApi{

         return Retrofit.Builder()
             .baseUrl("http://example.com/")
             .addConverterFactory(GsonConverterFactory.create())
             .client(client())
             .build()
             .create(VideoApi::class.java)
    }


    private fun client(): OkHttpClient{
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(MockResponseInterceptor(context))
        return builder.build()
    }


}