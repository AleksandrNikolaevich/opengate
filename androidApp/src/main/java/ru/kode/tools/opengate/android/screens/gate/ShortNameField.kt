package ru.kode.tools.opengate.android.screens.gate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import ru.kode.tools.opengate.android.R

@Composable
fun ShortNameField(
    value: String,
    onChange: (value: String) -> Unit,
    error: String?
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    val tfv = remember {
        val textFieldValue = TextFieldValue(text = value, selection = TextRange(value.length))
        mutableStateOf(textFieldValue)
    }

    OutlinedTextField(
        value = tfv.value,
        onValueChange = {
            tfv.value = it
            onChange(it.text)
        },
        label = { Text(text = stringResource(id = R.string.gate_short_name)) },
        placeholder = { Text(text = stringResource(id = R.string.gate_short_name_placeholder)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        isError = error != null,
        supportingText = {
            if (error != null) Text(text = error)
            else null
        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
    )
}