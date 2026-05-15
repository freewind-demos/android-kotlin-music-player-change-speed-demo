package com.freewind.musicplayer.changespeeddemo.player

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel

class PlayerViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val store = PlayerStore()
    private val handler = PlayerHandler(
        store = store,
        playbackSystemApi = AndroidMediaPlayerSystemApi(application.applicationContext),
        metadataSystemApi = AndroidAudioMetadataSystemApi(application.applicationContext),
    )

    val uiState = store.uiState

    fun onAudioPicked(uri: Uri) {
        handler.onAudioPicked(uri)
    }

    fun onPlayPauseClicked() {
        handler.onPlayPauseClicked()
    }

    fun onSpeedChanged(speed: Float) {
        handler.onSpeedChanged(speed)
    }

    override fun onCleared() {
        handler.release()
    }
}
