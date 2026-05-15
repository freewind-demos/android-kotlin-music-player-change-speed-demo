package com.freewind.musicplayer.changespeeddemo.player

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

class AndroidAudioMetadataSystemApi(
    private val context: Context,
) : AudioMetadataSystemApi {
    override fun resolveDisplayName(uri: Uri): String? {
        return context.contentResolver.query(
            uri,
            arrayOf(OpenableColumns.DISPLAY_NAME),
            null,
            null,
            null,
        )?.use { cursor ->
            val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index >= 0 && cursor.moveToFirst()) {
                cursor.getString(index)
            } else {
                null
            }
        }
    }
}
