package ru.kode.tools.opengate.android.screens.signin

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.kode.tools.opengate.android.R

@Composable
fun LoginField(
    value: String,
    onChange: (value: String) -> Unit,
    error: String?
) {
    OutlinedTextField(
        value = value,
        label = { Text(text = stringResource(id = R.string.phone)) },
        onValueChange = onChange,
        placeholder = { Text(text = stringResource(id = R.string.phone)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        singleLine = true,
        isError = error != null,
        supportingText = {
            if (error != null) Text(text = error)
            else null
        },
    )
}