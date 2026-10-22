package com.jozefv.o2app.feat_info.presenation.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.o2app.R
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import com.jozefv.o2app.core.presentation.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InfoViewModel(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {
    private val _eventChannel = Channel<InfoEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: InfoAction) {
        when (action) {
            is InfoAction.OnUriCallException -> {
                viewModelScope.launch(coroutineDispatcherProvider.mainImmediate) {
                    _eventChannel.send(
                        InfoEvent.Error(
                            UiText.StringResource(R.string.error_other)
                        )
                    )
                }
            }
            // We navigate directly in our UI
            is InfoAction.OnBackClick -> Unit
        }
    }
}