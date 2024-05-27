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

package io.github.antoinepirlot.satunes.ui.views.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.antoinepirlot.satunes.R
import io.github.antoinepirlot.satunes.database.services.settings.SettingsManager
import io.github.antoinepirlot.satunes.ui.components.dialog.InformationDialog
import io.github.antoinepirlot.satunes.ui.components.settings.SettingWithSwitch
import io.github.antoinepirlot.satunes.ui.components.settings.SubSetting
import io.github.antoinepirlot.satunes.ui.components.texts.Title
import io.github.antoinepirlot.satunes.database.R as RDb

/**
 * @author Antoine Pirlot on 27/05/2024
 */
@Composable
fun BatterySettingsView(
    modifier: Modifier = Modifier,
) {
    val audioOffloadChecked: Boolean by rememberSaveable { SettingsManager.audioOffloadChecked }
    var showAudioOffloadDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val context: Context = LocalContext.current

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Title(text = stringResource(id = R.string.battery_settings))
        SubSetting(title = stringResource(id = R.string.playback_settings)) {
            SettingWithSwitch(
                text = stringResource(id = RDb.string.audio_offload),
                checked = audioOffloadChecked,
                onCheckedChange = {
                    if (audioOffloadChecked) {
                        SettingsManager.switchAudioOffload(context = context)
                    } else {
                        showAudioOffloadDialog = true
                    }
                }
            )
        }
    }

    if (showAudioOffloadDialog && !audioOffloadChecked) {
        InformationDialog(
            title = stringResource(id = RDb.string.audio_offload),
            text = stringResource(id = RDb.string.audio_offload_info),
            onDismissRequest = { showAudioOffloadDialog = false },
            onConfirm = {
                showAudioOffloadDialog = false
                SettingsManager.switchAudioOffload(context = context)
            }
        )
    }
}

@Preview
@Composable
fun BatterySettingsViewPreview() {
    BatterySettingsView()
}