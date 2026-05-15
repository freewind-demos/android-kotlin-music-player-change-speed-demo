package com.freewind.musicplayer.changespeeddemo

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.freewind.musicplayer.changespeeddemo.player.PlayerViewModel
import com.freewind.musicplayer.changespeeddemo.ui.PlayerScreen

class MainActivity : ComponentActivity() {
    private val viewModel: PlayerViewModel by viewModels()

    private val pickAudioLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                try {
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION,
                    )
                } catch (_: SecurityException) {
                }
                viewModel.onAudioPicked(uri)
            }
        }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            PlayerScreen(
                state = state,
                onPickAudio = { pickAudioLauncher.launch(arrayOf("audio/*")) },
                onPlayPause = viewModel::onPlayPauseClicked,
                onSpeedChange = viewModel::onSpeedChanged,
            )
        }
    }
}

