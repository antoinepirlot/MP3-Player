package earth.mp3.ui.components.cards.folder

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CardFolder(
    modifier: Modifier = Modifier,
    folderName: String,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            TextButton(
                modifier = modifier,
                onClick = onClick //redirect to the folder's view
            ) {
                Text(text = folderName)
            }
        },
        leadingContent = {
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = "Arrow Right"
            )
        }
    )

    Divider(modifier = modifier)
}

@Composable
@Preview
fun CardFolderPreview() {

    CardFolder(folderName = "Nom de dossier", onClick = {/* TODO */ })
}