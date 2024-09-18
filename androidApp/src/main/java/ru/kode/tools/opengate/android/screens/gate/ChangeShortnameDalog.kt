package ru.kode.tools.opengate.android.screens.gate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.kode.tools.opengate.android.R

@Composable
fun ChangeShortnameDialog(
    shortName: String,
    onChangeShortName: (value: String) -> Unit,
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
) {
    Dialog(onDismissRequest = { onCancel() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
            ) {
                Text(stringResource(R.string.gate_change_name), fontSize = 20.sp)

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                ShortNameField(
                    value = shortName,
                    onChange = { onChangeShortName(it) },
                    error = null
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton (
                        onClick = onCancel
                    ){
                        Text(stringResource(R.string.cancel))
                    }

                    Button(
                        onClick = onSubmit
                    ){
                        Text(stringResource(R.string.save))
                    }
                }
            }

        }
    }
}