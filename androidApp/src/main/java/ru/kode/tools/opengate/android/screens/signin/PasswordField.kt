package ru.kode.tools.opengate.android.screens.signin

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.kode.tools.opengate.android.R

@Composable
fun PasswordField(
    value: String,
    onChange: (value: String) -> Unit,
    error: String?
) {
    val passwordHidden = remember { mutableStateOf(true) }

    OutlinedTextField(
        value = value,
        label = { Text(text = stringResource(id = R.string.password)) },
        onValueChange = onChange,
        placeholder = { Text(text = stringResource(id = R.string.password)) },
        singleLine = true,
        visualTransformation = if (passwordHidden.value) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = error?.isNotBlank() ?: false,
        supportingText = {
            if (error?.isNotBlank() == true) Text(text = error)
            else null
        },
        trailingIcon = {
            IconButton(onClick = { passwordHidden.value = !passwordHidden.value }) {
                if (passwordHidden.value) {
                    SwitchIcon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(id = R.string.show_password),
                    )
                } else {
                    SwitchIcon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(id = R.string.hide_password),
                    )
                }
            }
        }
    )
}

@Composable
fun SwitchIcon(
    imageVector: ImageVector,
    contentDescription: String,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.primary
    )
}