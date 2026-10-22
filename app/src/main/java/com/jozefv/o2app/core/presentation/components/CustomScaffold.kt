@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.o2app.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.o2app.R
import com.jozefv.o2app.core.presentation.components.button.CustomOutlinedButton
import com.jozefv.o2app.core.presentation.components.button.CustomPrimaryButton
import com.jozefv.o2app.core.presentation.model.CustomIconPainter
import com.jozefv.o2app.core.presentation.theme.O2AppTheme

@Composable
fun CustomScaffold(
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    topAppBar: @Composable () -> Unit = {},
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    primaryButton: @Composable (() -> Unit)? = null,
    outlinedButton: @Composable (() -> Unit)? = null,
) {
    Scaffold(
        modifier = modifier,
        containerColor = containerColor,
        topBar = topAppBar,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .padding(CustomSpaces.L)
                    // If there is button at the bottom of the screen, consider its position
                    // and display snackBar above it
                    .then(
                        if (primaryButton != null && outlinedButton != null) {
                            Modifier.padding(bottom = CustomSpaces.L * 5)
                        } else if (primaryButton != null || outlinedButton != null) {
                            Modifier.padding(bottom = CustomSpaces.L * 2)
                        } else {
                            Modifier
                        }
                    ),
                hostState = snackBarHostState
            ) { snackBarData ->
                CustomSnackBar(snackBarData = snackBarData)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = CustomSpaces.L,
                    end = CustomSpaces.L,
                    top = 0.dp,
                    bottom = CustomSpaces.L
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                content()
            }
            if ((primaryButton != null || outlinedButton != null)) {
                SpacerVerL()
            }
            primaryButton?.invoke()
            outlinedButton?.let {
                SpacerVerS()
                it.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun CustomScaffoldPreview() {
    O2AppTheme {
        val scrollBehavior =
            TopAppBarDefaults.pinnedScrollBehavior()
        CustomScaffold(
            modifier = Modifier.fillMaxSize(),
            topAppBar = {
                CustomTopAppBar(
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.fillMaxWidth(),
                    title = "Example screen"
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .verticalScroll(rememberScrollState()),
                    content = {
                    }
                )
            },
            primaryButton = {
                CustomPrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = CustomIconPainter(
                        iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                        iconDescription = null
                    ),
                    text = "Forward",
                    onClick = {}
                )
            },
            outlinedButton = {
                CustomOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = CustomIconPainter(
                        iconPainter = painterResource(R.drawable.arrow_forward_24_px),
                        iconDescription = null
                    ),
                    text = "Activate",
                    onClick = {}
                )
            }
        )
    }
}