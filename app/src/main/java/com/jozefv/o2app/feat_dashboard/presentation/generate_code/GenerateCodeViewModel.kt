package com.jozefv.o2app.feat_dashboard.presentation.generate_code

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class GenerateCodeViewModel : ViewModel() {
    private val _state = MutableStateFlow(GenerateCodeState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<GenerateCodeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: GenerateCodeAction) {
        when (action) {
            is GenerateCodeAction.OnGenerateClick -> {}
            is GenerateCodeAction.OnSaveClick -> {}
            // We navigate directly in our UI
            is GenerateCodeAction.OnBackClick -> Unit
        }
    }
}