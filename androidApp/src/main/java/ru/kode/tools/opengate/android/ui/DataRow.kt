package ru.kode.tools.opengate.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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