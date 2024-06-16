package ru.kode.tools.opengate.android.screens.profile

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.kode.tools.opengate.android.R

@Composable
fun ConfirmLogoutAlert(
    onLogout: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.are_you_sure))
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                onLogout()
            }) {
                Text(text = stringResource(id = R.string.logout))
            }
        }
    )
}