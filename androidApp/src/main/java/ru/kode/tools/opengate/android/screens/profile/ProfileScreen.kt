package ru.kode.tools.opengate.android.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kode.tools.opengate.android.R
import ru.kode.tools.opengate.presentation.presentation.ProfileViewModel
import org.koin.androidx.compose.get
import ru.kode.tools.opengate.android.ui.DataRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onPressBack: () -> Unit,
    viewModel: ProfileViewModel = get(),
) {
    val confirmLogoutOpen = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.profile),
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
        ) {
            DataRow(
                icon = Icons.Outlined.Logout,
                title = stringResource(id = R.string.logout),
                subtitle = stringResource(id = R.string.logout_description),
                onPress = {
                    confirmLogoutOpen.value = true
                }
            )
        }

        if (confirmLogoutOpen.value) {
            ConfirmLogoutAlert(
                onLogout = { viewModel.logout() },
                onDismiss = { confirmLogoutOpen.value = false }
            )
        }

    }
}