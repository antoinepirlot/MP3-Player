/*
 *  This file is part of MP3 Player.
 *
 *  MP3 Player is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  MP3 Player is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with MP3 Player.
 *  If not, see <https://www.gnu.org/licenses/>.

 *  ***** INFORMATIONS ABOUT THE AUTHOR *****
 *  The author of this file is Antoine Pirlot, the owner of this project.
 *  You find this original project on github.
 *
 *  My github link is: https://github.com/antoinepirlot
 *  This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 *  You can contact me via my email: pirlot.antoine@outlook.com
 * PS: I don't answer quickly.
 */

package earth.mp3player.ui.appBars

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Album
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import earth.mp3player.models.MenuTitle
import earth.mp3player.router.media.MediaDestination
import earth.mp3player.services.SettingsManager

@Composable
fun MP3BottomAppBar(
    modifier: Modifier = Modifier,
    startDestination: MutableState<String>
) {
    val menuTitleList: MutableList<MenuTitle> = mutableListOf(
        MenuTitle.FOLDERS,
        MenuTitle.ARTISTS,
        MenuTitle.ALBUMS,
        MenuTitle.GENRES,
        MenuTitle.MUSIC,
    )
    SettingsManager.menuTitleCheckedMap.forEach { (menuTitle: MenuTitle, checked: MutableState<Boolean>) ->
        if (!checked.value) {
            menuTitleList.remove(menuTitle)
        }
    }
    val selectedSection: MutableState<MenuTitle> =
        // Update the tab by default if settings has changed
        if (SettingsManager.foldersChecked.value) {
            rememberSaveable { mutableStateOf(MenuTitle.FOLDERS) }
        } else if (SettingsManager.artistsChecked.value) {
            rememberSaveable { mutableStateOf(MenuTitle.ARTISTS) }
        } else if (SettingsManager.albumsChecked.value) {
            rememberSaveable { mutableStateOf(MenuTitle.ALBUMS) }
        } else if (SettingsManager.genreChecked.value) {
            rememberSaveable { mutableStateOf(MenuTitle.GENRES) }
        } else {
            rememberSaveable { mutableStateOf(MenuTitle.MUSIC) }
        }

    NavigationBar(
        modifier = modifier
    ) {
        menuTitleList.forEach { section: MenuTitle ->
            NavigationBarItem(
                label = { Text(text = stringResource(id = section.stringId)) },
                selected = selectedSection.value == section,
                onClick = {
                    selectedSection.value = section
                    when (section) {
                        MenuTitle.FOLDERS -> {
                            startDestination.value = MediaDestination.FOLDERS.link
                        }

                        MenuTitle.ARTISTS -> {
                            startDestination.value = MediaDestination.ARTISTS.link
                        }

                        MenuTitle.ALBUMS -> {
                            startDestination.value = MediaDestination.ALBUMS.link
                        }

                        MenuTitle.GENRES -> {
                            startDestination.value = MediaDestination.GENRES.link
                        }

                        MenuTitle.MUSIC -> {
                            startDestination.value = MediaDestination.MUSICS.link
                        }

                    }
                },
                icon = {
                    when (section) {
                        MenuTitle.FOLDERS -> {
                            Icon(
                                imageVector = Icons.Rounded.Folder,
                                contentDescription = "Folder Icon"
                            )
                        }

                        MenuTitle.ARTISTS -> {
                            Icon(
                                imageVector = Icons.Rounded.AccountCircle,
                                contentDescription = "Artist Icon"
                            )

                        }

                        MenuTitle.ALBUMS -> {
                            Icon(
                                imageVector = Icons.Rounded.Album,
                                contentDescription = "Album Icon"
                            )
                        }

                        MenuTitle.GENRES -> {
                            Icon(
                                imageVector = Icons.Rounded.Category,
                                contentDescription = "Genres Icon"
                            )
                        }

                        MenuTitle.MUSIC -> {
                            Icon(
                                imageVector = Icons.Rounded.Audiotrack,
                                contentDescription = "Music Icon"
                            )

                        }
                    }
                }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun MP3BottomAppBarPreview() {
    MP3BottomAppBar(startDestination = mutableStateOf(MediaDestination.FOLDERS.link))
}