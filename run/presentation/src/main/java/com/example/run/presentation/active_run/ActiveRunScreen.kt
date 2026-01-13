@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.run.presentation.active_run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.presentation.designsystem.RuniqueTheme
import com.example.core.presentation.designsystem.StartIcon
import com.example.core.presentation.designsystem.StopIcon
import com.example.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.example.core.presentation.designsystem.components.RuniqueScaffold
import com.example.core.presentation.designsystem.components.RuniqueToolbar
import com.example.run.presentation.R
import com.example.run.presentation.active_run.components.RunDataCart
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunRoot(
    activeRunViewModel: ActiveRunViewModel = koinViewModel()
) {
    val state by activeRunViewModel.state.collectAsStateWithLifecycle()
    ActiveRunScreen(
        state = state,
        onActions = activeRunViewModel::onActions,
    )
}

@Composable
fun ActiveRunScreen(
    state: ActiveRunState,
    onActions: (ActiveRunActions) -> Unit,
) {
    RuniqueScaffold(
        withGradient = false,
        topAppBar = {
            RuniqueToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBackClick = {
                    onActions(ActiveRunActions.OnBackClick)
                },
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                iconSize = 20.dp,
                onClick = {
                    onActions(ActiveRunActions.OnToggleRunClick)
                },
                contentDescription = if (state.shouldTrack) stringResource(R.string.pause_run) else {
                    stringResource(R.string.start_run)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunDataCart(
                modifier = Modifier.fillMaxWidth().padding(16.dp).padding(innerPadding),
                elapsedTime = state.elapsedTime,
                runData = state.runData
            )
        }
    }
}

@Preview
@Composable
fun ActiveRunScreenPreview() {
    RuniqueTheme {
        ActiveRunScreen(
            ActiveRunState(),
            {}
        )
    }
}