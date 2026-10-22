package com.jozefv.o2app.feat_dashboard.presentation.activate_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import com.jozefv.o2app.core.domain.util.ResultHandler
import com.jozefv.o2app.core.presentation.util.toUiText
import com.jozefv.o2app.feat_dashboard.domain.util.o2.Repository
import com.jozefv.o2app.feat_dashboard.presentation.mapper.toCodeStatusUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActivateCodeViewModel(
    private val repository: Repository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {
    private val _state = MutableStateFlow(ActivateCodeState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<ActivateCodeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        // If this VM exists -> there cannot be null. E.g. if process death -
        // don't persist the state - just start app again check [MainActivity]
        viewModelScope.launch(coroutineDispatcherProvider.mainImmediate) {
            _state.update {
                it.copy(
                    codeStatusUi = repository.observeCode().filterNotNull().first().toCodeStatusUi()
                )
            }
        }
    }

    fun onAction(action: ActivateCodeAction) {
        when (action) {
            is ActivateCodeAction.OnActivateClick -> {
                state.value.codeStatusUi?.let {
                    viewModelScope.launch(coroutineDispatcherProvider.mainImmediate) {
                        _state.update { it.copy(isLoading = true) }
                        when (val result = repository.activateCode(it.code)) {
                            is ResultHandler.Error -> {
                                _eventChannel.send(
                                    ActivateCodeEvent.Error(result.error.toUiText())
                                )
                            }

                            is ResultHandler.Success -> {
                                _eventChannel.send(ActivateCodeEvent.ActivationSuccess)
                            }
                        }
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
            // We navigate directly in our UI
            is ActivateCodeAction.OnBackClick -> Unit
        }
    }
}