/*
 * This file is part of MP3 Player.
 *
 * MP3 Player is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
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

package earth.mp3player.services.router

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import earth.mp3player.router.Destination
import earth.mp3player.router.main.MainDestination
import earth.mp3player.router.media.MediaDestination
import earth.mp3player.services.settings.SettingsManager

/**
 * @author Antoine Pirlot on 13/03/2024
 */
object RouterManager {
    var startMainDestination: MutableState<MainDestination> = mutableStateOf(MainDestination.ROOT)
    var currentMainDestination: MutableState<MainDestination> = startMainDestination

    var startMediaDestination: MutableState<MediaDestination> =
        if (SettingsManager.foldersChecked.value) {
            mutableStateOf(MediaDestination.FOLDERS)
        } else if (SettingsManager.artistsChecked.value) {
            mutableStateOf(MediaDestination.ARTISTS)
        } else if (SettingsManager.albumsChecked.value) {
            mutableStateOf(MediaDestination.ALBUMS)
        } else {
            mutableStateOf(MediaDestination.MUSICS)
        }
    var currentMediaDestination: MutableState<MediaDestination> = startMediaDestination

    fun navigate(navController: NavController, destination: Destination) {
        when(destination) {
            is MainDestination -> {
                currentMainDestination.value = destination
            }

            is MediaDestination -> {
                currentMediaDestination.value = destination
            }
        }
        navController!!.navigate(destination.link)
    }
}