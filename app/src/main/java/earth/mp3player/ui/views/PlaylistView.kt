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
 *
 *  ***** INFORMATIONS ABOUT THE AUTHOR *****
 *  The author of this file is Antoine Pirlot, the owner of this project.
 *  You find this original project on github.
 *
 *  My github link is: https://github.com/antoinepirlot
 *  This current project's link is: https://github.com/antoinepirlot/MP3-Player
 *
 *  You can contact me via my email: pirlot.antoine@outlook.com
 *  PS: I don't answer quickly.
 */

package earth.mp3player.ui.views

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.PlaylistAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import earth.mp3player.database.models.tables.Playlist
import earth.mp3player.database.services.DatabaseManager
import earth.mp3player.ui.components.forms.PlaylistCreationForm

/**
 * @author Antoine Pirlot on 30/03/2024
 */

@Composable
fun PlaylistView(
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }
    FloatingActionButton(onClick = { openAlertDialog = true }) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.PlaylistAdd,
            contentDescription = "Create a playlist button"
        )
    }
    when {
        openAlertDialog -> {
            PlaylistCreationForm(
                onConfirm = { playlistTitle: String ->
                    val playlist: Playlist = Playlist(id = 0, title = playlistTitle)
                    DatabaseManager(context = context).insert(playlist)
                    openAlertDialog = false
                },
                onDismissRequest = { openAlertDialog = false }
            )
        }
    }
}

@Preview
@Composable
fun PlaylistViewPreview() {
    PlaylistView()
}