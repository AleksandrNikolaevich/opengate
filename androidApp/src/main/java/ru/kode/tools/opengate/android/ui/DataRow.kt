package ru.kode.tools.opengate.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kode.tools.opengate.story.Story

@Composable
fun DataRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onPress: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onPress()
            }

    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = icon,
                contentDescription = title,
            )

            Column {
                Text(
                    text = title,
                    fontSize = 20.sp
                )
                Text(
                    text = subtitle,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview
@Story
@Composable
fun DataRowPreview(
    title: String = "Logout",
    subtitle: String = "You will need to login again"
) {
    DataRow(
        icon = Icons.Outlined.Logout,
        title = title,
        subtitle = subtitle,
        onPress = { }
    )
}