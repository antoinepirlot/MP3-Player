package earth.mp3

import android.Manifest.permission.READ_MEDIA_AUDIO
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import earth.mp3.models.Artist
import earth.mp3.models.Folder
import earth.mp3.models.Music
import earth.mp3.router.Router
import earth.mp3.ui.components.MP3TopAppBar
import earth.mp3.ui.theme.MP3Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        if (!isAudioDenied()) {
            setContent {
                val navController = rememberNavController()

                val musicList = remember { mutableStateListOf<Music>() }
                val rootFolderList = remember { mutableStateListOf<Folder>() }
                val artistList = remember { mutableStateListOf<Artist>() }

                Music.loadData(
                    context = LocalContext.current,
                    musicList = musicList,
                    rootFolderList = rootFolderList,
                    artistList = artistList
                )

                MP3Theme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val scrollBehavior =
                            TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                        Scaffold(
                            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                            topBar = { MP3TopAppBar(scrollBehavior = scrollBehavior) },
                        ) { innerPadding ->
                            Column(
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                Router(
                                    modifier = Modifier,
                                    navController = navController,
                                    musicList = musicList,
                                    folderList = rootFolderList,
                                    artistList = artistList,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestPermissionLauncher(): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        when {
            isAudioDenied() -> {
                requestPermissionLauncher().launch(READ_MEDIA_AUDIO)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, READ_MEDIA_AUDIO) -> {
                // Additional rationale should be displayed
            }

            else -> {
                // Permission has not been asked yet
                requestPermissionLauncher().launch(READ_MEDIA_AUDIO)
            }
        }
    }

    private fun isAudioDenied(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            READ_MEDIA_AUDIO
        ) == PackageManager.PERMISSION_DENIED
    }
}