package com.jozefv.o2app.feat_dashboard.presentation.activate_code

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ActivateCodeViewModel : ViewModel() {
    private val _state = MutableStateFlow(ActivateCodeState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<ActivateCodeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: ActivateCodeAction) {
        when (action) {
            is ActivateCodeAction.OnActivateClick -> {

            }
            // We navigate directly in our UI
            is ActivateCodeAction.OnBackClick -> Unit
        }
    }
}