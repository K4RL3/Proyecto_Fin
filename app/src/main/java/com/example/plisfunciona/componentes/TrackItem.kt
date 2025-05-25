package com.example.plisfunciona.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.plisfunciona.modelo.Track
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size

@Composable
fun TrackItem(
    track: Track,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = track.album.images.firstOrNull()?.url,
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )

        Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
            Text(track.name, maxLines = 1)
            Text(track.artists.joinToString { it.name }, style = MaterialTheme.typography.bodySmall)
        }

        Text(track.duration_ms.toTimeString(), style = MaterialTheme.typography.bodySmall)
    }
}

fun Int.toTimeString(): String {
    val minutes = (this / 1000) / 60
    val seconds = (this / 1000) % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}