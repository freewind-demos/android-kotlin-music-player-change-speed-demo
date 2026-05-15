package com.freewind.musicplayer.changespeeddemo.player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.net.Uri

class AndroidMediaPlayerSystemApi(
    private val context: Context,
) : AudioPlaybackSystemApi {
    private var mediaPlayer: MediaPlayer? = null

    override fun load(
        uri: Uri,
        onPrepared: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit,
    ) {
        release()

        val player = MediaPlayer()
        mediaPlayer = player

        try {
            player.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build(),
            )
            player.setDataSource(context, uri)
            player.setOnPreparedListener {
                onPrepared()
            }
            player.setOnCompletionListener {
                onCompletion()
            }
            player.setOnErrorListener { _, what, extra ->
                release()
                onError("MediaPlayer error what=$what extra=$extra")
                true
            }
            player.prepareAsync()
        } catch (t: Throwable) {
            release()
            onError(t.message ?: "加载音频失败")
        }
    }

    override fun play() {
        mediaPlayer?.start()
    }

    override fun pause() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.pause()
            }
        }
    }

    override fun seekToStart() {
        mediaPlayer?.seekTo(0)
    }

    override fun setSpeed(speed: Float) {
        mediaPlayer?.playbackParams = PlaybackParams().apply {
            this.speed = speed
            pitch = 1f
        }
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
