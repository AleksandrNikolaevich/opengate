package ru.kode.tools.opengate.android.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kode.tools.opengate.android.R
import ru.kode.tools.opengate.app.presentation.HomeViewModel
import org.koin.androidx.compose.get
import ru.kode.tools.opengate.android.ui.SwipableRow
import ru.kode.tools.opengate.features.gates.domain.Gate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = get(),
    onOpenProfile: () -> Unit,
    onOpenDetails: (id: String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val pullToRefreshState = rememberPullToRefreshState()

    val showLoading = state.gates?.isEmpty() ?: true && state.isLoading && !pullToRefreshState.isRefreshing

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.forceReload()
        }
    }

    if (!state.isLoading) {
        LaunchedEffect(true) {
            pullToRefreshState.endRefresh()
        }
    }

    LaunchedEffect(true) {
        viewModel.run()
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            stringResource(id = R.string.app_name),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                        )
                        if (showLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(32.dp)
                                    .padding(start = 6.dp, bottom = 2.dp),
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onOpenProfile) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = stringResource(id = R.string.profile),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddings ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            Box(Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(state.gates?.size ?: 0) { index ->
                        val item = state.gates!![index]
                        val gateState = item.state

                        SwipableRow(
                            rightSideWidth = 80.dp,
                            right = { dismiss ->
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.secondary)
                                        .clickable {
                                            onOpenDetails(item.id)
                                            dismiss()
                                        }){
                                    Icon(
                                        modifier = Modifier.size(32.dp),
                                        imageVector = Icons.Outlined.Settings,
                                        contentDescription = stringResource(id = R.string.profile),
                                        tint = MaterialTheme.colorScheme.onSecondary
                                    )
                                }
                            },
                        ) {
                            ListItem(
                                modifier = Modifier.clickable {
                                    viewModel.openGate(item)
                                },
                                headlineContent = {
                                    Text(
                                        text = item.shortName ?: item.name,
                                        fontSize = 20.sp
                                    )
                                },
                                overlineContent = {
                                    if (item.isAvailable)
                                        Text(text = "ONLINE", color = Color.Gray)
                                    else
                                        Text(text = "OFFLINE", color = Color.Red)

                                    Text(
                                        text = if (item.isAvailable)
                                                stringResource(R.string.online).uppercase()
                                            else
                                                stringResource(R.string.offline).uppercase()
                                    )
                                },
                                trailingContent = {
                                    when (gateState) {
                                        Gate.OpenState.PENDING -> {}
                                        Gate.OpenState.OPENING -> {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(24.dp),
                                            )
                                        }
                                        Gate.OpenState.OPENED -> {
                                            Icon(
                                                modifier = Modifier.size(32.dp),
                                                imageVector = Icons.Outlined.CheckCircle,
                                                contentDescription = stringResource(id = R.string.opened),
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Gate.OpenState.ERROR -> {}
                                    }
                                }
                            )
                        }
                    }
                }
                PullToRefreshContainer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullToRefreshState,
                )
            }
        }
    }
}
