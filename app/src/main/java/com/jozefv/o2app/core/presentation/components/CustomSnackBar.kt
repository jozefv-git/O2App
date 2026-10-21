package com.jozefv.o2app.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomSnackBar(snackBarData: SnackbarData, modifier: Modifier = Modifier, maxLines: Int = 2) {
    Snackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.inverseSurface,
        contentColor = MaterialTheme.colorScheme.inverseOnSurface,
        content = {
            Text(
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                text = snackBarData.visuals.message,
                style = MaterialTheme.typography.labelLarge
            )
        }
    )
}

@Preview
@Composable
private fun CustomSnackBarPreview() {
    val snackBarHostState = remember { SnackbarHostState() }
    CustomScaffold(
        modifier = Modifier.fillMaxSize(),
        snackBarHostState = snackBarHostState,
        content = {
            LaunchedEffect(true) {
                snackBarHostState.showSnackbar("Example snack message.")
            }
        }
    )
}

@Preview
@Composable
private fun CustomSnackBarLongPreview() {
    val snackBarHostState = remember { SnackbarHostState() }
    CustomScaffold(
        modifier = Modifier.fillMaxSize(),
        snackBarHostState = snackBarHostState,
        content = {
            LaunchedEffect(true) {
                snackBarHostState.showSnackbar(
                    "Example snack message which is way more longer. " +
                            "So we can verify long text overflow.",
                    duration = SnackbarDuration.Long
                )
            }
        }
    )
}