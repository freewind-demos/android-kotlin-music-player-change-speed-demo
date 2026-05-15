package com.freewind.musicplayer.changespeeddemo.player

import android.net.Uri

class PlayerHandler(
    private val store: PlayerStore,
    private val playbackSystemApi: AudioPlaybackSystemApi,
    private val metadataSystemApi: AudioMetadataSystemApi,
) {
    fun onAudioPicked(uri: Uri) {
        val title = metadataSystemApi.resolveDisplayName(uri)
            ?: uri.lastPathSegment
            ?: "已选音频"

        store.setAudio(uri, title)
        playbackSystemApi.load(
            uri = uri,
            onPrepared = {
                store.setPrepared(true)
                store.setPlaybackEnded(false)
                playbackSystemApi.setSpeed(store.uiState.value.speed)
                playbackSystemApi.play()
                store.setPlaying(true)
                store.setError(null)
            },
            onCompletion = {
                playbackSystemApi.seekToStart()
                store.setPlaying(false)
                store.setPlaybackEnded(true)
            },
            onError = { message ->
                store.setPrepared(false)
                store.setPlaying(false)
                store.setPlaybackEnded(false)
                store.setError(message)
            },
        )
    }

    fun onPlayPauseClicked() {
        val state = store.uiState.value
        if (state.audioUri == null) {
            store.setError("先选一首本地音频")
            return
        }

        if (state.isPlaying) {
            playbackSystemApi.pause()
            store.setPlaying(false)
            return
        }

        if (!state.isPrepared) {
            store.setError("音频仍在加载，或上次加载失败")
            return
        }

        if (state.playbackEnded) {
            playbackSystemApi.seekToStart()
        }
        playbackSystemApi.setSpeed(state.speed)
        playbackSystemApi.play()
        store.setPrepared(true)
        store.setPlaying(true)
        store.setPlaybackEnded(false)
        store.setError(null)
    }

    fun onSpeedChanged(speed: Float) {
        store.setSpeed(speed)
        if (store.uiState.value.isPrepared) {
            playbackSystemApi.setSpeed(speed)
        }
    }

    fun release() {
        playbackSystemApi.release()
    }
}
