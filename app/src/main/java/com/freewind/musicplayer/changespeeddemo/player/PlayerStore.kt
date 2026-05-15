package com.freewind.musicplayer.changespeeddemo.player

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PlayerUiState(
    val audioUri: Uri? = null,
    val title: String = "未选择音频",
    val isPrepared: Boolean = false,
    val isPlaying: Boolean = false,
    val playbackEnded: Boolean = false,
    val speed: Float = 1f,
    val errorMessage: String? = null,
)

class PlayerStore {
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun setAudio(uri: Uri, title: String) {
        _uiState.update {
            it.copy(
                audioUri = uri,
                title = title,
                isPrepared = false,
                isPlaying = false,
                playbackEnded = false,
                errorMessage = null,
            )
        }
    }

    fun setPrepared(isPrepared: Boolean) {
        _uiState.update { it.copy(isPrepared = isPrepared) }
    }

    fun setPlaying(isPlaying: Boolean) {
        _uiState.update { it.copy(isPlaying = isPlaying) }
    }

    fun setPlaybackEnded(playbackEnded: Boolean) {
        _uiState.update { it.copy(playbackEnded = playbackEnded) }
    }

    fun setSpeed(speed: Float) {
        _uiState.update { it.copy(speed = speed) }
    }

    fun setError(message: String?) {
        _uiState.update { it.copy(errorMessage = message) }
    }
}
