package com.example.videoone.ui.main

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.videoone.R
import com.example.videoone.model.Video
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.C.TYPE_HLS
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Video rendering activity,
 * will be set to fullscreen
 **/
class VideoActivity : AppCompatActivity() {

    private var currentWindow: Int = 0
    private var playbackPosition: Long = C.TIME_UNSET
    private val viewModel: MainViewModel by viewModel()
    private lateinit var exoPlayer: SimpleExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        currentWindow = intent.getIntExtra(MEDIA_INDEX, -1)

        savedInstanceState?.let {
            playbackPosition = it.getLong(PLAYBACK_POSITION)
            currentWindow = it.getInt(CURRENT_WINDOW)
        }

        back.setOnClickListener {
            this@VideoActivity.finish()
        }
    }

    private fun initPlayer() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MOVIE)
            .build()

        exoPlayer = SimpleExoPlayer.Builder(this).build().apply {
            setWakeMode(C.WAKE_MODE_NETWORK)
            setHandleAudioBecomingNoisy(true)
            setAudioAttributes(audioAttributes, true)
        }

        playerView.player = exoPlayer

        exoPlayer.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {
                val tag = timeline.getWindow(
                    exoPlayer.currentWindowIndex,
                    Timeline.Window()
                ).tag as? String
                tag?.let {
                    video_title.text = it
                }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                when (playbackState) {
                    //close activity at the end of playlist
                    Player.STATE_ENDED -> {
                        this@VideoActivity.finish()
                    }
                }

            }

        })
    }

    private fun setFullScreen() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                )
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val params = playerView.layoutParams as ConstraintLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        playerView.layoutParams = params
    }

    private fun buildMediaSource(video: Video): MediaSource {

        val dataSourceFactory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(this, getString(R.string.app_name))
        )

        val uri = Uri.parse(video.sourceUrl)

        //set video names as tags will use them for displaying titles
        return when (Util.inferContentType(uri, null)) {
            TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory)
                .setTag(video.videoName)
                .createMediaSource(uri)

            else -> ProgressiveMediaSource.Factory(dataSourceFactory)
                .setTag(video.videoName)
                .createMediaSource(uri)
        }
    }

    private fun playList(videoList: List<Video>): MediaSource? {
        val mediaSources = arrayOfNulls<MediaSource>(videoList.size)
        for (i in videoList.indices) {
            mediaSources[i] =
                buildMediaSource(videoList[i])
        }

        return if (mediaSources.size == 1) mediaSources[0]
        else ConcatenatingMediaSource(*mediaSources)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putLong(PLAYBACK_POSITION, playbackPosition)
            putInt(CURRENT_WINDOW, currentWindow)
        }
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.playWhenReady = false
        playbackPosition = exoPlayer.currentPosition
        currentWindow = exoPlayer.currentWindowIndex
        exoPlayer.release()
    }

    override fun onStart() {
        super.onStart()
        viewModel.videoList.observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                initPlayer()
                val playList = playList(list)
                playList?.let {
                    exoPlayer.prepare(it)
                    //play from the item selected on the playlist
                    exoPlayer.seekTo(currentWindow, playbackPosition);
                    exoPlayer.playWhenReady = true

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setFullScreen()
    }

    companion object {
        const val MEDIA_INDEX: String = "media_index"
        const val PLAYBACK_POSITION = "playbackPosition"
        const val CURRENT_WINDOW = "currentWindow"
    }
}