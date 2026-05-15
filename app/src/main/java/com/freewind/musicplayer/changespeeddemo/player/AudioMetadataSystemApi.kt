package com.freewind.musicplayer.changespeeddemo.player

import android.net.Uri

interface AudioMetadataSystemApi {
    fun resolveDisplayName(uri: Uri): String?
}

