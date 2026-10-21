package com.jozefv.o2app.feat_dashboard.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.o2app.R
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
import com.jozefv.o2app.core.presentation.util.UiText
import com.jozefv.o2app.feat_dashboard.domain.util.o2.Repository
import com.jozefv.o2app.feat_dashboard.presentation.mapper.toCodeStatusUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class HomeViewModel(
    private val repository: Repository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<HomeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        repository.observeCode().onEach { code ->
            _state.update {
                it.copy(
                    codeStatus = code?.toCodeStatusUi()
                )
            }
        }.launchIn(viewModelScope + coroutineDispatcherProvider.mainImmediate)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnActivateClick -> {
                viewModelScope.launch(coroutineDispatcherProvider.mainImmediate) {
                    val codeStatus = state.value.codeStatus
                    when {
                        codeStatus == null -> {
                            _eventChannel.send(
                                HomeEvent.Error(
                                    UiText.StringResource(R.string.error_code_empty)
                                )
                            )
                        }

                        codeStatus.isActive -> {
                            _eventChannel.send(
                                HomeEvent.Error(
                                    UiText.StringResource(R.string.error_code_already_activated)
                                )
                            )
                        }

                        !codeStatus.isActive -> {
                            _eventChannel.send(HomeEvent.Activate)
                        }
                    }
                }
            }
            // We navigate directly in our UI
            is HomeAction.OnInfoClick -> Unit
            is HomeAction.OnGenerateClick -> Unit
        }
    }
}