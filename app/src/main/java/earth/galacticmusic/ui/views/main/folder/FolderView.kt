/*
 * This file is part of MP3 Player.
 *
 * MP3 Player is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * MP3 Player is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MP3 Player.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * **** INFORMATIONS ABOUT THE AUTHOR *****
 * The author of this file is Antoine Pirlot, the owner of this project.
 * You find this original project on github.
 *
 * My github link is: https://github.com/antoinepirlot
 * This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 * You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package earth.galacticmusic.ui.views.main.folder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import earth.galacticmusic.database.models.Folder
import earth.galacticmusic.database.models.Media
import earth.galacticmusic.database.models.Music
import earth.galacticmusic.playback.services.playback.PlaybackController
import earth.galacticmusic.router.media.utils.openCurrentMusic
import earth.galacticmusic.router.media.utils.openMedia
import earth.galacticmusic.router.media.utils.openMediaFromFolder
import earth.galacticmusic.router.media.utils.resetOpenedPlaylist
import earth.galacticmusic.ui.views.main.MediaListView
import java.util.SortedMap

/**
 * @author Antoine Pirlot on 01/04/2024
 */

@Composable
fun FolderView(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    folder: Folder,
) {
    val playbackController: PlaybackController = PlaybackController.getInstance()
    val folderMusicMediaItemSortedMap: SortedMap<Music, MediaItem> = remember {
        folder.musicMediaItemSortedMap
    }
    val mapToShow: SortedMap<Long, Media> = sortedMapOf()

    //Load sub-folders
    mapToShow.putAll(folder.getSubFolderListAsMedia())

    //Load sub-folder's musics
    folderMusicMediaItemSortedMap.forEach { (music: Music, _) ->
        mapToShow[music.id] = music
    }

    resetOpenedPlaylist()
    MediaListView(
        modifier = modifier,
        mediaMap = mapToShow,

        openMedia = { clickedMedia: Media ->
            openMediaFromFolder(navController, clickedMedia)
        },

        shuffleMusicAction = {
            playbackController.loadMusic(
                musicMediaItemSortedMap = folder.getAllMusic(),
                shuffleMode = true
            )
            openMedia(navController = navController)
        },

        onFABClick = { openCurrentMusic(navController) }
    )
}

@Preview
@Composable
fun FolderViewPreview() {
    FolderView(
        navController = rememberNavController(),
        folder = Folder(id = 0, title = "Folder title", context = LocalContext.current)
    )
}