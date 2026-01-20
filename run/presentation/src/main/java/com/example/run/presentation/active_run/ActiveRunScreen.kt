@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.presentation.designsystem.RuniqueTheme
import com.example.core.presentation.designsystem.StartIcon
import com.example.core.presentation.designsystem.StopIcon
import com.example.core.presentation.designsystem.components.RuniqueDialog
import com.example.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.example.core.presentation.designsystem.components.RuniqueOutlinedActionButton
import com.example.core.presentation.designsystem.components.RuniqueScaffold
import com.example.core.presentation.designsystem.components.RuniqueToolbar
import com.example.run.presentation.R
import com.example.run.presentation.active_run.components.RunDataCart
import com.example.run.presentation.util.hasLocationPermission
import com.example.run.presentation.util.hasNotificationPermission
import com.example.run.presentation.util.shouldShowLocationPermissionRationale
import com.example.run.presentation.util.shouldShowNotificationPermissionRationale
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
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        //here perms will only check if now granted like if we grant notification
        //but location denied it will ask again for permission only for location
        //here the hasNotificationPermission will show false because not granted now even though it's granted before
        val hasCourseLocationPermission = perms[
            Manifest.permission.ACCESS_COARSE_LOCATION
        ] == true
        val hasFineLocationPermission = perms[
            Manifest.permission.ACCESS_FINE_LOCATION
        ] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 33) {
            perms[Manifest.permission.POST_NOTIFICATIONS] == true
        } else {
            true
        }

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onActions(
            ActiveRunActions.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCourseLocationPermission && hasFineLocationPermission,
                showLocationPermissionRationale = showLocationRationale
            )
        )
        onActions(
            ActiveRunActions.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = hasNotificationPermission,
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
        val locationPermissionGranted = context.hasLocationPermission()
        val notificationPermissionGranted = context.hasNotificationPermission()

        //rationale will only show true when the user deny it first time at the same run
        //so if user deny and launch it again it will not show true for rationale
        //in this case we have to check if rationale is false for location and notification no dialog is showing
        //that's why when rationale is false we have to check if location or notification permissions not granted
        //to show dialog to take user to intent since permission can't be requested after many declines
        if (!showLocationRationale && !showNotificationRationale && !(locationPermissionGranted && notificationPermissionGranted)) {
            onActions(ActiveRunActions.ForcePermissionDialog)
        }
    }

    LaunchedEffect(true) {
        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onActions(
            ActiveRunActions.SubmitLocationPermissionInfo(
                acceptedLocationPermission = context.hasLocationPermission(),
                showLocationPermissionRationale = showLocationRationale
            )
        )
        onActions(
            ActiveRunActions.SubmitNotificationPermissionInfo(
                acceptedNotificationPermission = context.hasNotificationPermission(),
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
        if (!showLocationRationale && !showNotificationRationale) {
            permissionLauncher.requestRuniquePermission(context = context)
        }
    }

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(innerPadding),
                elapsedTime = state.elapsedTime,
                runData = state.runData
            )
        }
    }
    if (state.showLocationRationale || state.showNotificationRationale) {
        RuniqueDialog(
            title = stringResource(R.string.permission_required),
            onDismiss = {},
            description = when {
                state.showLocationRationale && state.showNotificationRationale -> {
                    stringResource(R.string.location_notification_rationale)
                }

                state.showLocationRationale -> {
                    stringResource(R.string.location_rationale)
                }

                else -> {
                    stringResource(R.string.notification_rationale)
                }

            },
            primaryButton = {
                RuniqueOutlinedActionButton(
                    text = stringResource(R.string.okay),
                    isLoading = false,
                    onClick = {
                        onActions(ActiveRunActions.DismissRationaleDialog)
                        permissionLauncher.requestRuniquePermission(context = context)
                    }
                )
            },
            secondaryButton = {}
        )
    }
    if (state.isPermissionDialogForced) {
        val hasLocationPermission = context.hasLocationPermission()
        val hasNotificationPermission = context.hasNotificationPermission()
        RuniqueDialog(
            title = stringResource(R.string.permission_required),
            onDismiss = {
                onActions(ActiveRunActions.DismissPermissionDialog)
            },
            description = when {
                !hasLocationPermission && !hasNotificationPermission -> {
                    stringResource(R.string.location_notification_rationale)
                }

                !hasLocationPermission -> {
                    stringResource(R.string.location_rationale)
                }

                else -> {
                    stringResource(R.string.notification_rationale)
                }
            },
            primaryButton = {
                RuniqueOutlinedActionButton(
                    text = stringResource(R.string.okay),
                    isLoading = false,
                    onClick = {
                        onActions(ActiveRunActions.DismissPermissionDialog)
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.fromParts("package", context.packageName, null)
                        context.startActivity(intent)
                    }
                )
            },
            secondaryButton = {}
        )
    }
}

private fun ActivityResultLauncher<Array<String>>.requestRuniquePermission(
    context: Context
) {
    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else arrayOf()

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermission + notificationPermission)
        }

        !hasLocationPermission -> {
            launch(locationPermission)
        }

        !hasNotificationPermission -> {
            launch(notificationPermission)
        }
    }
}

@Preview
@Composable
fun ActiveRunScreenPreview() {
    RuniqueTheme {
        ActiveRunScreen(
            ActiveRunState()
        ) {}
    }
}