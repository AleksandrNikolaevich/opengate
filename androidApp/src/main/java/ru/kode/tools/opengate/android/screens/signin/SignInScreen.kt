package ru.kode.tools.opengate.android.screens.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ru.kode.tools.opengate.android.R
import ru.kode.tools.opengate.presentation.presentation.SignInViewModel
import org.koin.androidx.compose.get

@Composable
fun SignInScreen(viewModel: SignInViewModel = get()) {
    val state by viewModel.state.collectAsState()
    val login: String by viewModel.login.collectAsState()
    val password: String by viewModel.password.collectAsState()

    Scaffold { paddings ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = stringResource(id = R.string.app_name),
                fontSize = TextUnit(26f, TextUnitType.Sp),
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.primary
            )

            LoginField(value = login, onChange = { viewModel.login.value = it }, error = if (state.error.isNullOrEmpty()) null else "")

            PasswordField(value = password, onChange = { viewModel.password.value = it }, error = state.error)

            SubmitButton(
                loading = state.isLoading,
                enabled = true,
                onClick = { viewModel.signIn() }
            )
        }
    }
}

@Composable
fun SubmitButton(
    enabled: Boolean,
    loading: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.padding(16.dp),
        enabled = enabled,
        onClick = onClick
    ) {
        if (loading) CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.inversePrimary
        )
        else Text(text = stringResource(id = R.string.sign_in))
    }
}

@Preview
@Composable
fun SignInPreview() {
    SignInScreen()
}