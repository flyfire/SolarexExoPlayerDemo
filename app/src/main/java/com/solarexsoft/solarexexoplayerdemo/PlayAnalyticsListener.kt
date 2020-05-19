package com.solarexsoft.solarexexoplayerdemo

import android.util.Log
import android.view.Surface
import com.google.android.exoplayer2.analytics.AnalyticsListener

/**
 * Created by houruhou on 2020/5/19/3:50 PM
 * Desc:
 */
class PlayAnalyticsListener : AnalyticsListener {
    companion object {
        const val TAG = "PlayAnalyticsListener"
    }
    override fun onRenderedFirstFrame(eventTime: AnalyticsListener.EventTime?, surface: Surface?) {
        Log.d(TAG, "onRenderedFirstFrame windowIndex = ${eventTime?.windowIndex},currentPlaybackPositionMs = ${eventTime?.currentPlaybackPositionMs}")
    }

    override fun onDroppedVideoFrames(
        eventTime: AnalyticsListener.EventTime?,
        droppedFrames: Int,
        elapsedMs: Long
    ) {
        Log.d(TAG, "onDroppedVideoFrames windowIndex = ${eventTime?.windowIndex}, currentPlaybackPositionMs = ${eventTime?.currentPlaybackPositionMs}, droppedFrames = $droppedFrames")
    }

    override fun onAudioUnderrun(
        eventTime: AnalyticsListener.EventTime?,
        bufferSize: Int,
        bufferSizeMs: Long,
        elapsedSinceLastFeedMs: Long
    ) {
        Log.d(TAG, "onAudioUnderrun windowIndex = ${eventTime?.windowIndex},currentPlaybackPositionMs = ${eventTime?.currentPlaybackPositionMs}, bufferSize = $bufferSize")
    }
}