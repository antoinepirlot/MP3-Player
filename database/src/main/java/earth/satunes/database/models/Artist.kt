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

package earth.satunes.database.models

import androidx.compose.ui.graphics.ImageBitmap
import androidx.media3.common.MediaItem
import java.util.SortedMap

/**
 * @author Antoine Pirlot on 27/03/2024
 */

data class Artist(
    override val id: Long = nextId,
    override var title: String,
    var musicList: MutableList<Music> = mutableListOf(),
    var albumSortedMap: SortedMap<String, Album> = sortedMapOf(),
) : Media {
    override var artwork: ImageBitmap? = null
    override val musicMediaItemSortedMap: SortedMap<Music, MediaItem> = sortedMapOf()

    companion object {
        var nextId: Long = 1
    }

    init {
        nextId++
    }

    fun addAlbum(album: Album) {
        this.albumSortedMap.putIfAbsent(album.title, album)
    }

    fun addMusic(music: Music) {
        musicList.add(music)
        musicMediaItemSortedMap.putIfAbsent(music, music.mediaItem)
    }

    override fun toString(): String {
        return this.title
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Artist

        return title == other.title
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}