@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.o2app.feat_info.presenation.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.o2app.R
import com.jozefv.o2app.core.presentation.components.CustomScaffold
import com.jozefv.o2app.core.presentation.components.CustomTopAppBar
import com.jozefv.o2app.core.presentation.components.button.CustomPrimaryButton
import com.jozefv.o2app.core.presentation.components.text.CustomScreenDescriptionText
import com.jozefv.o2app.core.presentation.components.text.CustomScreenHighlightText
import com.jozefv.o2app.core.presentation.model.CustomIconPainter
import com.jozefv.o2app.core.presentation.theme.O2AppTheme
import com.jozefv.o2app.core.presentation.util.ObserveAsEvents
import com.jozefv.o2app.core.presentation.util.toSafeUriOpener
import com.jozefv.o2app.feat_info.domain.util.InfoUrlConstants
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun InfoScreenRoot(
    viewModel: InfoViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            is InfoEvent.Error -> {
                scope.launch {
                    snackBarHostState.showSnackbar(event.error.asString(context))
                }
            }
        }
    }

    InfoScreen(
        modifier = Modifier.fillMaxSize(),
        snackBarHostState = snackBarHostState,
        onAction = { action ->
            if (action is InfoAction.OnBackClick) {
                onBackClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun InfoScreen(
    snackBarHostState: SnackbarHostState,
    onAction: (InfoAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val localUriHandler = LocalUriHandler.current
    CustomScaffold(
        modifier = modifier.fillMaxSize(),
        topAppBar = {
            CustomTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.info_top_app_bar),
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
                    text = stringResource(R.string.info_highlight)
                )
                CustomScreenDescriptionText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.info_description)
                )
            }
        },
        primaryButton = {
            CustomPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.button_linkedin),
                leadingIcon = CustomIconPainter(
                    iconPainter = painterResource(R.drawable.link_24_px)
                ),
                onClick = {
                    localUriHandler.toSafeUriOpener(InfoUrlConstants.LINKED_IN)?.let { exception ->
                        onAction(InfoAction.OnUriCallException(exception))
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun HomeScreenWelcomeLightThemePreview() {
    O2AppTheme(darkTheme = false) {
        InfoScreen(
            modifier = Modifier.fillMaxSize(),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenWelcomeDarkThemePreview() {
    O2AppTheme(darkTheme = true) {
        InfoScreen(
            modifier = Modifier.fillMaxSize(),
            snackBarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}