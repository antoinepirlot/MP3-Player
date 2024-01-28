package earth.mp3.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import earth.mp3.models.ExoPlayerManager
import earth.mp3.ui.components.music.MusicControlBar

@Composable
fun PlayBackView(
    modifier: Modifier = Modifier,
) {
    val exoPlayerManager = ExoPlayerManager.getInstance(null)
    val musicPlaying = remember { mutableStateOf(exoPlayerManager.getMusicPlaying()!!) }
    Column(
        modifier = modifier
    ) {
        Text(text = musicPlaying.value.name)
        MusicControlBar(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            musicPlaying = musicPlaying
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun PlayBackViewPreview() {
    PlayBackView()
}