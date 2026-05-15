package com.freewind.musicplayer.changespeeddemo.player

import android.net.Uri

interface AudioPlaybackSystemApi {
    fun load(
        uri: Uri,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
    )

    fun play()
    fun pause()
    fun seekToStart()
    fun setSpeed(speed: Float)
    fun release()
}

