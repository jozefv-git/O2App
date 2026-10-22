@file:OptIn(ExperimentalMaterial3Api::class)

package com.jozefv.o2app.feat_dashboard.presentation.generate_code

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.jozefv.o2app.feat_dashboard.presentation.components.card.CustomCodeCard
import com.jozefv.o2app.feat_dashboard.presentation.preview.codeStatusUiPreview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GenerateCodeScreenRoot(
    viewModel: GenerateCodeViewModel = koinViewModel(),
    onSaveSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            is GenerateCodeEvent.SaveSuccess -> {
                onSaveSuccess()
            }
        }
    }

    GenerateCodeScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onAction = { action ->
            if (action is GenerateCodeAction.OnBackClick) {
                onBackClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun GenerateCodeScreen(
    state: GenerateCodeState,
    onAction: (GenerateCodeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var primaryButtonText = stringResource(R.string.button_generate_code)
    var primaryButtonPainter = painterResource(R.drawable.add_24_px)
    var highlightText = stringResource(R.string.generate_highlight_generate_code)
    var descriptionText = stringResource(R.string.generate_description_generate_code)
    if (state.currentGeneratedCode != null) {
        primaryButtonText = stringResource(R.string.button_regenerate_code)
        primaryButtonPainter = painterResource(R.drawable.refresh_24_px)
        highlightText = stringResource(R.string.generate_highlight_regenerate_code)
        descriptionText = stringResource(R.string.generate_description_regenerate_code)
    }


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
                        onAction(GenerateCodeAction.OnBackClick)
                    }
                ),
                title = stringResource(R.string.generate_top_app_bar),
                scrollBehavior = scrollBehavior
            )
        },
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
                CustomCodeCard(
                    modifier = Modifier.fillMaxWidth(),
                    code = state.newGeneratedCode ?: state.currentGeneratedCode
                    ?: stringResource(R.string.content_empty)
                )
            }
        },
        primaryButton = {
            CustomPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                isLoading = state.isGenerateLoading,
                enabled = state.isGenerateButtonEnabled,
                text = primaryButtonText,
                leadingIcon = CustomIconPainter(iconPainter = primaryButtonPainter),
                onClick = { onAction(GenerateCodeAction.OnGenerateClick) }
            )
        },
        outlinedButton = {
            CustomOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                isLoading = state.isSaveLoading,
                enabled = state.isSaveButtonEnabled,
                text = stringResource(R.string.button_save_code),
                leadingIcon = CustomIconPainter(
                    iconPainter = painterResource(R.drawable.save_24_px)
                ),
                onClick = {
                    onAction(GenerateCodeAction.OnSaveClick)
                }
            )
        }
    )
}

@Preview
@Composable
private fun GenerateCodeScreenEmptyPreview() {
    O2AppTheme {
        GenerateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = GenerateCodeState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun GenerateCodeScreenLoadingPreview() {
    O2AppTheme {
        GenerateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = GenerateCodeState(isGenerateLoading = true),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun GenerateCodeScreenInitialPreview() {
    O2AppTheme {
        GenerateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = GenerateCodeState(
                currentGeneratedCode = null,
                newGeneratedCode = null,
                isGenerateLoading = false,
                isSaveLoading = false
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun GenerateCodeScreenCodeLoadingPreview() {
    O2AppTheme {
        GenerateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = GenerateCodeState(
                currentGeneratedCode = null,
                newGeneratedCode = null,
                isGenerateLoading = true,
                isSaveLoading = false
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun GenerateCodeScreenCodePreview() {
    O2AppTheme {
        GenerateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = GenerateCodeState(
                currentGeneratedCode = null,
                newGeneratedCode = codeStatusUiPreview.code,
                isGenerateLoading = false,
                isSaveLoading = false
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun GenerateCodeScreenSameCodePreview() {
    O2AppTheme {
        GenerateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = GenerateCodeState(
                currentGeneratedCode = codeStatusUiPreview.code,
                newGeneratedCode = codeStatusUiPreview.code,
                isGenerateLoading = false,
                isSaveLoading = false
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun GenerateCodeScreenSaveCodeLoadingPreview() {
    O2AppTheme {
        GenerateCodeScreen(
            modifier = Modifier.fillMaxSize(),
            state = GenerateCodeState(
                currentGeneratedCode = null,
                newGeneratedCode = codeStatusUiPreview.code,
                isGenerateLoading = true,
                isSaveLoading = true
            ),
            onAction = {}
        )
    }
}
