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
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.C.TYPE_HLS
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class VideoActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val fullscreen = false
    private lateinit var exoPlayer: SimpleExoPlayer
    private var mediaIndex: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        mediaIndex = intent.getIntExtra(MEDIA_INDEX, -1)

        setFullScreen()

        viewModel.videoList.observe(this, Observer { list ->
            if (list.isNotEmpty()) {
                initPlayer()
                val playList = playList(list)
                playList?.let {
                    exoPlayer.prepare(it)
                    //play from the item selected on the playlist
                    exoPlayer.seekTo(mediaIndex, C.TIME_UNSET);
                    exoPlayer.playWhenReady = true

                }
            }
        })
    }

    fun initPlayer() {
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
            override fun onTimelineChanged(timeline: Timeline, manifest: Any?, reason: Int) {}
            override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {}
            override fun onLoadingChanged(isLoading: Boolean) {}
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {}
            override fun onRepeatModeChanged(repeatMode: Int) {}
            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
            override fun onPlayerError(error: ExoPlaybackException) {}
            override fun onPositionDiscontinuity(reason: Int) {
                //val metadata = CustomMetadata.get(exoPlayer.currentTag)

                val latestWindowIndex: Int = exoPlayer.getCurrentWindowIndex()
                if (latestWindowIndex != mediaIndex) {
                    // item selected in playlist has changed, handle here
                }
            }
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
            override fun onSeekProcessed() {}
        })
    }

    fun setFullScreen() {
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
        //fullscreen = true
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

    override fun onPause() {
        super.onPause()
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.release()

    }


    companion object {
        val MEDIA_INDEX: String = "media_index"
    }
}