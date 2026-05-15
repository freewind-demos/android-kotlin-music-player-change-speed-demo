package com.freewind.musicplayer.changespeeddemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.freewind.musicplayer.changespeeddemo.player.PlayerUiState
import java.util.Locale

private val SpeedOptions = listOf(0.5f, 0.75f, 1f, 1.5f, 2f, 3f)

@Composable
fun PlayerScreen(
    state: PlayerUiState,
    onPickAudio: () -> Unit,
    onPlayPause: () -> Unit,
    onSpeedChange: (Float) -> Unit,
) {
    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Music Speed Demo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "本地选歌 → 内置 MediaPlayer → 速度调节",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Icon(imageVector = Icons.Default.MusicNote, contentDescription = null)
                            Column {
                                Text(text = state.title, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = if (state.isPlaying) "播放中" else "已暂停",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }

                        Text(
                            text = "速度：${speedText(state.speed)}",
                            style = MaterialTheme.typography.bodyLarge,
                        )

                        state.errorMessage?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = onPickAudio) {
                        Text("选歌")
                    }
                    Button(
                        onClick = onPlayPause,
                        enabled = state.audioUri != null,
                    ) {
                        Icon(
                            imageVector = if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (state.isPlaying) "暂停" else "播放")
                    }
                }

                Text(
                    text = "速度档位",
                    style = MaterialTheme.typography.titleMedium,
                )

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    SpeedOptions.forEach { speed ->
                        FilterChip(
                            selected = state.speed == speed,
                            onClick = { onSpeedChange(speed) },
                            label = { Text(speedText(speed)) },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "支持本地音频文件；选完自动开始播放。",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun speedText(speed: Float): String {
    return when {
        speed % 1f == 0f -> String.format(Locale.US, "%.0fx", speed)
        (speed * 10f) % 1f == 0f -> String.format(Locale.US, "%.1fx", speed)
        else -> String.format(Locale.US, "%.2fx", speed)
    }
}
