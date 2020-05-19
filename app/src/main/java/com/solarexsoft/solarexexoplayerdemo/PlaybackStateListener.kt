package com.solarexsoft.solarexexoplayerdemo

import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

/**
 * Created by houruhou on 2020/5/19/3:39 PM
 * Desc:
 */
class PlaybackStateListener : Player.EventListener {
    companion object {
        const val TAG = "PlaybackStateListener"
    }
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        var stateString = ""
        when(playbackState) {
            ExoPlayer.STATE_IDLE -> {
                stateString = "state idle"
            }
            ExoPlayer.STATE_BUFFERING -> {
                stateString = "state buffering"
            }
            ExoPlayer.STATE_READY -> {
                stateString = "state ready"
            }
            ExoPlayer.STATE_ENDED -> {
                stateString = "state ended"
            }
            else -> {
                stateString = "unknown state"
            }
        }
        Log.d(TAG, "change state to $stateString,playWhenReady = $playWhenReady")
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        Log.d(TAG, "isPlaying = $isPlaying")
    }
}