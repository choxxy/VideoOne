package com.example.videoone.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.videoone.R
import com.example.videoone.model.Video
import kotlinx.android.synthetic.main.layout_video_item.view.*

class VideoAdapter() : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

     var videoList = mutableListOf<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_video_item, parent, false))

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position])
    }

    fun setVideos(videos: List<Video>) {
        videoList.addAll(videos)
        notifyDataSetChanged()
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(video: Video) {
            itemView.apply {
                title.text = video.videoName
            }
        }
    }
}