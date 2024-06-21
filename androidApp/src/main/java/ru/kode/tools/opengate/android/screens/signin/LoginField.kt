package ru.kode.tools.opengate.android.screens.signin

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.kode.tools.opengate.android.R

@Composable
fun LoginField(
    value: String,
    onChange: (value: String) -> Unit,
    error: String?
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

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
        modifier = Modifier.focusRequester(focusRequester)
    )
}