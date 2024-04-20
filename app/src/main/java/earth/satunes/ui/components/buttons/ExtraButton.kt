/*
 * This file is part of Satunes.
 *
 *  Satunes is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software Foundation,
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  Satunes is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Satunes.
 *  If not, see <https://www.gnu.org/licenses/>.
 *
 *  **** INFORMATIONS ABOUT THE AUTHOR *****
 *  The author of this file is Antoine Pirlot, the owner of this project.
 *  You find this original project on github.
 *
 *  My github link is: https://github.com/antoinepirlot
 *  This current project's link is: https://github.com/antoinepirlot/Satunes
 *
 *  You can contact me via my email: pirlot.antoine@outlook.com
 *  PS: I don't answer quickly.
 */

package earth.satunes.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import earth.satunes.ui.views.SatunesIcons

/**
 * @author Antoine Pirlot on 20/04/2024
 */

@Composable
fun ExtraButton(
    modifier: Modifier = Modifier,
    icon: SatunesIcons,
    description: String? = null,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier.padding(bottom = 8.dp),
        onClick = onClick
    ) {
        @Suppress("NAME_SHADOWING")
        var description: String = description?: icon.description
        if (description.isBlank()) {
            description = icon.description
        }
        Icon(imageVector = icon.imageVector, contentDescription = description)
    }
}

@Preview
@Composable
fun ExtraButtonPreview() {
    ExtraButton(
        icon = SatunesIcons.PLAYLIST_ADD,
        onClick = {}
    )
}