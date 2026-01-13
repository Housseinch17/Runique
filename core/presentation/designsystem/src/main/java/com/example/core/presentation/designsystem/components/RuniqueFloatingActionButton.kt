package com.example.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.presentation.designsystem.RunIcon
import com.example.core.presentation.designsystem.RuniqueTheme

@Composable
fun RuniqueFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null,
    iconSize: Dp = 25.dp
) {
    Box(
        modifier = modifier.background(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            shape = CircleShape
        ),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(
            modifier = Modifier.padding(10.dp),
            onClick = onClick,
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize),
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun RuniqueFloatingActionButtonPreview() {
    RuniqueTheme {
        RuniqueFloatingActionButton(
            icon = RunIcon,
            onClick = {}
        )
    }
}