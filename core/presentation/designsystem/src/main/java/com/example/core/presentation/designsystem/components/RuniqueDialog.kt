package com.example.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.presentation.designsystem.RuniqueTheme

@Composable
fun RuniqueDialog(
    modifier: Modifier = Modifier,
    title: String,
    onDismiss: () -> Unit,
    description: String,
    primaryButton: @Composable () -> Unit,
    secondaryButton: @Composable () -> Unit,
) {
    AlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        containerColor = MaterialTheme.colorScheme.background,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = { primaryButton() },
        dismissButton = { secondaryButton() }
    )
}

@Preview
@Composable
fun RuniqueDialogPreview() {
    RuniqueTheme {
        RuniqueDialog(
            modifier = Modifier,
            title = "Running is paused",
            onDismiss = {},
            description = "Do you want to resume or finish workout",
            primaryButton = {
                Button(
                    onClick = {}
                ) {
                    Text("Resume")
                }
            },
            secondaryButton = {
                OutlinedButton(
                    onClick = {},
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
                ) {
                    Text("Finish")
                }
            },
        )
    }
}