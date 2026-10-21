@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.o2app.feat_dashboard.presentation.activate_code

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
fun ActivateCodeScreenRoot(
    viewModel: ActivateCodeViewModel = koinViewModel(),
    onActivationSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            is ActivateCodeEvent.Error -> {
                scope.launch {
                    snackBarHostState.showSnackbar(event.error.asString(context))
                }
            }

            is ActivateCodeEvent.ActivationSuccess -> {
                onActivationSuccess()
            }
        }
    }
    ActivateCodeScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        snackBarHostState = snackBarHostState,
        onAction = { action ->
            if (action is ActivateCodeAction.OnBackClick) {
                onBackClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ActivateCodeScreen(
    state: ActivateCodeState,
    snackBarHostState: SnackbarHostState,
    onAction: (ActivateCodeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    CustomScaffold(
        modifier = modifier.fillMaxSize(),
        topAppBar = {
            CustomTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = CustomIconPainter(
                    iconPainter = painterResource(R.drawable.arrow_back_24_px),
                    iconDescription = stringResource(R.string.button_back),
                    onClick = {
                        onAction(ActivateCodeAction.OnBackClick)
                    }
                ),
                title = stringResource(R.string.activate_top_app_bar),
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
                    text = stringResource(R.string.activate_highlight_good_luck)
                )
                CustomScreenDescriptionText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.activate_description_good_luck)
                )
                state.codeStatusUi?.let {
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
                isLoading = state.isLoading,
                text = stringResource(R.string.button_activate_code),
                leadingIcon = CustomIconPainter(
                    iconPainter = painterResource(R.drawable.check_24_px)
                ),
                onClick = {
                    onAction(ActivateCodeAction.OnActivateClick)
                }
            )
        }
    )
}

@Preview
@Composable
private fun ActivateCodeScreenLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        ActivateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = ActivateCodeState(
                codeStatusUi = codeStatusUiPreview,
                isLoading = false
            ),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun ActivateCodeScreenLoadingLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        ActivateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = ActivateCodeState(
                codeStatusUi = codeStatusUiPreview,
                isLoading = true
            ),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun ActivateCodeScreenDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        ActivateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = ActivateCodeState(
                codeStatusUi = codeStatusUiPreview,
                isLoading = false
            ),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun ActivateCodeScreenLoadingDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        ActivateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = ActivateCodeState(
                codeStatusUi = codeStatusUiPreview,
                isLoading = true
            ),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}