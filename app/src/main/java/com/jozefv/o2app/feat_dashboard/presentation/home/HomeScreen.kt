@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.o2app.feat_dashboard.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jozefv.o2app.R
import com.jozefv.o2app.core.presentation.components.CustomScaffold
import com.jozefv.o2app.core.presentation.components.CustomTopAppBar
import com.jozefv.o2app.core.presentation.components.button.CustomOutlinedButton
import com.jozefv.o2app.core.presentation.components.button.CustomPrimaryButton
import com.jozefv.o2app.core.presentation.components.text.CustomScreenDescriptionText
import com.jozefv.o2app.core.presentation.components.text.CustomScreenHighlightText
import com.jozefv.o2app.core.presentation.model.CustomIconPainter
import com.jozefv.o2app.core.presentation.theme.O2AppTheme
import com.jozefv.o2app.core.presentation.util.ObserveAsEvents
import com.jozefv.o2app.feat_dashboard.presentation.components.card.CustomCodeStatusCard
import com.jozefv.o2app.feat_dashboard.presentation.preview.codeStatusUiPreview
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onInfoClick: () -> Unit,
    onGenerateClick: () -> Unit,
    onActivateClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            is HomeEvent.Error -> {
                scope.launch {
                    snackBarHostState.showSnackbar(event.error.asString(context))
                }
            }

            is HomeEvent.Activate -> {
                onActivateClick()
            }
        }
    }
    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        snackBarHostState = snackBarHostState,
        onAction = { action ->
            if (action is HomeAction.OnGenerateClick) {
                onGenerateClick()
            }
            if(action is HomeAction.OnInfoClick){
                onInfoClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    snackBarHostState: SnackbarHostState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var highlightText = stringResource(R.string.home_highlight_welcome)
    var descriptionText = stringResource(R.string.home_description_welcome)
    state.codeStatus?.let { codeStatusUi ->
        if (codeStatusUi.isActive) {
            highlightText = stringResource(R.string.home_highlight_great)
            descriptionText = stringResource(R.string.home_description_great)
        } else {
            highlightText = stringResource(R.string.home_highlight_almost)
            descriptionText = stringResource(R.string.home_description_almost)
        }
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    CustomScaffold(
        modifier = modifier.fillMaxSize(),
        topAppBar = {
            CustomTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.home_top_app_bar),
                trailingContent = listOf(
                    CustomIconPainter(
                        iconPainter =
                            painterResource(R.drawable.info_24_px),
                        iconDescription = stringResource(R.string.button_info),
                        onClick = {
                            onAction(HomeAction.OnInfoClick)
                        }
                    )
                ),
                scrollBehavior = scrollBehavior
            )
        },
        snackBarHostState = snackBarHostState,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .verticalScroll(rememberScrollState())
            ) {
                CustomScreenHighlightText(
                    modifier = Modifier.fillMaxWidth(),
                    text = highlightText
                )
                CustomScreenDescriptionText(
                    modifier = Modifier.fillMaxWidth(),
                    text = descriptionText
                )
                state.codeStatus?.let {
                    CustomCodeStatusCard(
                        modifier = Modifier.fillMaxWidth(),
                        code = it.code,
                        isCodeActive = it.isActive
                    )
                }
            }
        },
        primaryButton = {
            CustomPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.button_generate_code),
                leadingIcon = CustomIconPainter(
                    iconPainter = painterResource(R.drawable.arrow_forward_24_px)
                ),
                onClick = {
                    onAction(HomeAction.OnGenerateClick)
                }
            )
        },
        outlinedButton = {
            CustomOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.button_activate_code),
                leadingIcon = CustomIconPainter(
                    iconPainter = painterResource(R.drawable.arrow_forward_24_px)
                ),
                onClick = {
                    onAction(HomeAction.OnActivateClick)
                }
            )
        }
    )
}

@Preview
@Composable
private fun HomeScreenWelcomeLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(codeStatus = null),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenInactiveLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(codeStatus = codeStatusUiPreview),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenActiveLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(codeStatus = codeStatusUiPreview.copy(isActive = true)),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenWelcomeDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(codeStatus = null),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenInactiveDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(codeStatus = codeStatusUiPreview),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenActiveDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            state = HomeState(codeStatus = codeStatusUiPreview.copy(isActive = true)),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}