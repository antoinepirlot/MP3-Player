package earth.mp3.router

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import earth.mp3.models.Artist
import earth.mp3.models.Folder
import earth.mp3.models.Music
import earth.mp3.ui.components.cards.CardList

@Composable
fun Router(
    modifier: Modifier = Modifier,
    startDestination: String,
    folderListToShow: MutableList<Folder>,
    artistListToShow: MutableList<Artist>,
    musicListToShow: MutableList<Music>,
    folderMap: Map<Long, Folder>
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.FOLDERS.link) {
            CardList(
                objectList = folderListToShow,
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Arrow Forward",
                onClick = { folder: Folder ->
                    navController.navigate("${Destination.FOLDERS.link}/${folder.id}")
                }
            )
        }

        composable("${Destination.FOLDERS.link}/{id}") {
            val folder = folderMap[it.arguments!!.getString("id")!!.toLong()]
            CardList(
                objectList = folder!!.getSubFolderList(),
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Arrow Forward",
                onClick = { /*TODO*/ }
            )
        }

        composable(Destination.ARTISTS.link) {
            CardList(
                objectList = artistListToShow,
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Account Circle",
                onClick = { /*TODO show artist albums list*/ }
            )
        }

        composable(Destination.MUSICS.link) {
            CardList(
                objectList = musicListToShow,
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play Arrow",
                onClick = { /*TODO play music*/ }
            )
        }
    }
}