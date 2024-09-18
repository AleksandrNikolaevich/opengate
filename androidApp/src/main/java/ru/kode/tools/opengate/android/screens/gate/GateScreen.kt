package ru.kode.tools.opengate.android.screens.gate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.kode.tools.opengate.android.R
import ru.kode.tools.opengate.app.presentation.GateViewModel
import org.koin.androidx.compose.get
import ru.kode.tools.opengate.android.ui.DataRow
import ru.kode.tools.opengate.features.gates.domain.Gate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GateScreen(
    gateId: String,
    onPressBack: () -> Unit,
    viewModel: GateViewModel = get()
) {

    val state by viewModel.state.collectAsState()
    val shortNameFieldValue: String? by viewModel.shortNameFieldValue.collectAsState()

    val shortName = state.shortName

    var shortNameIsOpened by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.run(gateId)
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        state.gate?.name ?: stringResource(id = R.string.gate),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onPressBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                },
            )
        }
    ) { paddings ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            DataRow(
                icon = Icons.Outlined.Info,
                title = shortName ?: "",
                subtitle = stringResource(id = R.string.gate_short_name),
                onPress = {
                    shortNameIsOpened = true
                }
            )

            DataRow(
                icon =  if (state.gate?.isAvailable == true)
                            Icons.Outlined.Cloud
                        else
                            Icons.Outlined.CloudOff,
                title = if (state.gate?.isAvailable == true)
                        stringResource(R.string.online)
                    else
                        stringResource(R.string.offline),
                subtitle = stringResource(id = R.string.gate_status),
                onPress = {}
            )

            OpenGateButton(
                enabled = state.gate?.isAvailable == true,
                loading = state.gate?.state == Gate.OpenState.OPENING,
                done = state.gate?.state == Gate.OpenState.OPENED,
                onClick = { viewModel.openGate() }
            )

            if (shortNameIsOpened) {
                ChangeShortnameDialog(
                    shortName = shortNameFieldValue ?: "",
                    onChangeShortName = {
                        viewModel.shortNameFieldValue.value = it
                    },
                    onCancel = {
                        viewModel.rollbackShortName()
                        shortNameIsOpened = false
                    },
                    onSubmit = {
                        shortNameIsOpened = false
                        viewModel.commitShortName()
                    }
                )
            }
        }
    }
}

@Composable
fun OpenGateButton(
    enabled: Boolean,
    loading: Boolean,
    done: Boolean,
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
        ) else if (done) Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = stringResource(id = R.string.go_back)
        )
        else Text(text = stringResource(id = R.string.gate_open))
    }
}