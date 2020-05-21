package com.solarexsoft.solarexexoplayerdemo

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.PlayerMessage
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    lateinit var playerView: PlayerView
    private var player: SimpleExoPlayer? = null
    var playWhenReady = true
    var currentWindow = 0
    var playbackPosition = 0L
    val playbackStateListener = PlaybackStateListener()
    val playAnalyticsListener = PlayAnalyticsListener()
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
//        hideSystemUi()
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
        val uri = Uri.parse("http://demos.webmproject.org/exoplayer/glass.mp4")
        val mediaSource = buildMediaSource(uri)
        val target = PlayerMessage.Target { messageType, payload ->
            Log.d(TAG, "messageType = $messageType")
            (payload as (() -> Unit)).invoke()
        }
        player!!.createMessage(target)
            .setPosition(3000)
            .setHandler(Handler(Looper.getMainLooper()))
            .setPayload({
                Log.d(TAG, "payload current position = " + player!!.currentPosition)
            })
            .setDeleteAfterDelivery(true)
            .send()
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.addListener(playbackStateListener)
        player!!.addAnalyticsListener(playAnalyticsListener)
        player!!.prepare(mediaSource, false, false)
        player!!.seekTo(3000)
        player!!.playWhenReady = playWhenReady
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
            player!!.removeListener(playbackStateListener)
            player!!.removeAnalyticsListener(playAnalyticsListener)
            player!!.release()
            player = null
        }
    }
}
