@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.o2app.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.o2app.R
import com.jozefv.o2app.core.presentation.model.CustomIconPainter
import com.jozefv.o2app.core.presentation.theme.O2AppTheme
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant


@Composable
fun CustomTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        scrolledContainerColor = MaterialTheme.colorScheme.surface,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface
    ),
    enabled: Boolean = true,
    rapidClickLimit: Duration? = 1.seconds,
    navigationIcon: CustomIconPainter? = null,
    trailingContent: List<CustomIconPainter>? = null,
    title: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    var clickedAt by remember { mutableStateOf(Instant.DISTANT_PAST) }
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CustomSpaces.S),
        colors = colors,
        navigationIcon = {
            navigationIcon?.let {
                IconButton(
                    enabled = enabled,
                    onClick = {
                        rapidClickLimit?.let { rapidClickLimit ->
                            if (clickedAt.plus(rapidClickLimit) < Clock.System.now()) {
                                it.onClick()
                            }
                        } ?: it.onClick()
                        clickedAt = Clock.System.now()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = it.iconPainter,
                        contentDescription = it.iconDescription
                    )
                }
            }
        },
        actions = {
            trailingContent?.let {
                it.forEach { customIconVector ->
                    IconButton(
                        enabled = enabled,
                        onClick = {
                            rapidClickLimit?.let { rapidClickLimit ->
                                if (clickedAt.plus(rapidClickLimit) < Clock.System.now()) {
                                    customIconVector.onClick()
                                }
                            } ?: customIconVector.onClick()
                            clickedAt = Clock.System.now()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = customIconVector.iconPainter,
                            contentDescription = customIconVector.iconDescription
                        )
                    }
                }
            }
        },
        title = {
            title?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = textStyle,
                    maxLines = 2
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    O2AppTheme {
        val scrollBehavior =
            TopAppBarDefaults.pinnedScrollBehavior(state = rememberTopAppBarState())
        CustomScaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topAppBar = {
                CustomTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Top",
                    navigationIcon = CustomIconPainter(
                        iconPainter = painterResource(R.drawable.arrow_back_24_px),
                        iconDescription = "Back",
                        onClick = {}
                    ),
                    scrollBehavior = scrollBehavior
                )
            },
            content = {

            }
        )
    }
}