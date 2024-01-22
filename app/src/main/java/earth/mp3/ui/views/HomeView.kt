package earth.mp3.ui.views

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.util.UnstableApi
import earth.mp3.R
import earth.mp3.models.Folder
import earth.mp3.models.Music
import earth.mp3.ui.components.cards.CardList
import earth.mp3.ui.components.cards.artists.CardArtistList
import earth.mp3.ui.components.cards.menu.CardMenuList
import earth.mp3.ui.components.cards.tracks.CardMusicList

/**
 * Show The Home App Bar and content inside
 */
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@OptIn(UnstableApi::class)
@Composable
fun HomeView(
    modifier: Modifier,
    musicList: List<Music>,
    folderList: List<Folder>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            val folderSelected = rememberSaveable { mutableStateOf(true) } // default section
            val artistsSelected = rememberSaveable { mutableStateOf(false) }
            val tracksSelected = rememberSaveable { mutableStateOf(false) }
            val folderListToShow = remember { mutableStateListOf<Folder>() }
            val musicListToShow = remember { mutableStateListOf<Music>() }


            CardMenuList(
                modifier = modifier,
                folderSelected = folderSelected,
                artistsSelected = artistsSelected,
                tracksSelected = tracksSelected,
                resetFoldersToShow = {
                    folderListToShow.clear()
                    for (folder in folderList) {
                        folderListToShow.add(folder)
                    }
                }
            )
            if (folderSelected.value) {
                CardList(
                    objectList = folderListToShow,
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Arrow Forward",
                    onClick = { loadSubFoldersTo(folderListToShow, it) }
                )
//                CardFolderList(
//                    modifier = modifier,
//                    folderList = folderListToShow,
//                    updateFolderList = {
//                        loadSubFoldersTo(folderListToShow, it)
//                    }
//                )
            } else if (artistsSelected.value) {
                CardArtistList(modifier = modifier)
            } else if (tracksSelected.value) {
                CardList(
                    objectList = musicListToShow,
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play Arrow",
                    onClick = { /*TODO play music*/ }
                )
                CardMusicList(modifier = modifier, musicList = musicList)
            } else {
                throw IllegalStateException(
                    "No tab selected (folder, artists, tracks), that could not happen"
                )
            }
        }
    }
}

@Composable
@Preview
fun HomeViewPreview() {
    HomeView(
        modifier = Modifier.fillMaxSize(),
        musicList = listOf(),
        folderList = listOf()
    )
}

fun loadSubFoldersTo(folderList: MutableList<Folder>, folder: Folder) {
    folderList.clear()
    val subFolderList = folder.getSubFolderList()
    if (subFolderList != null) {
        for (subFolder: Folder in subFolderList) {
            folderList.add(subFolder)
        }
    }

}

//if (musicList.isEmpty()) {
//    Text(text = "The music list is empty, please add music to your phone and restart")
//} else {
//    val player = ExoPlayer.Builder(LocalContext.current).build()
//    val playerControlView = PlayerControlView(LocalContext.current)
//    playerControlView.player = player
//
//    val mediaItem = MediaItem.fromUri(musicList[0].uri)
//    player.setMediaItem(mediaItem)
//    player.prepare()
//    player.play()
//    Text(text = musicList[0].name)
//}