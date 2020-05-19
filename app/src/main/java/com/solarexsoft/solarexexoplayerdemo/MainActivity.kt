package com.solarexsoft.solarexexoplayerdemo

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MainActivity : AppCompatActivity() {

    lateinit var playerView: PlayerView
    private var player: SimpleExoPlayer? = null
    var playWhenReady = true
    var currentWindow = 0
    var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerView = findViewById<PlayerView>(R.id.playerView)
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun initPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this)
        playerView.player = player
        val uri = Uri.parse("https://video.wu-mi.com/original/5ddd71f64acb441586016cd2fb0a9c8c/34bdf26-1710dc0140e.mp4?auth_key=1589868000-1973353835-0-e9a25000327da84074f42777a68c47db")
        val mediaSource = buildMediaSource(uri)
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab");
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }
}
