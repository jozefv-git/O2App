package com.jozefv.o2app.feat_dashboard.presentation.generate_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozefv.o2app.core.domain.util.CoroutineDispatcherProvider
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

class GenerateCodeViewModel(
    private val repository: Repository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {
    private val _state = MutableStateFlow(GenerateCodeState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<GenerateCodeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        repository.observeCode().onEach { o2Code ->
            _state.update {
                it.copy(currentGeneratedCode = o2Code?.toCodeStatusUi()?.code)
            }
        }.launchIn(viewModelScope + coroutineDispatcherProvider.mainImmediate)
    }

    fun onAction(action: GenerateCodeAction) {
        when (action) {
            is GenerateCodeAction.OnGenerateClick -> {
                viewModelScope.launch(coroutineDispatcherProvider.mainImmediate) {
                    _state.update { it.copy(isGenerateLoading = true) }
                    val newGeneratedCode = repository.generateCode()
                    _state.update {
                        it.copy(
                            newGeneratedCode = newGeneratedCode,
                            isGenerateLoading = false
                        )
                    }
                }
            }

            is GenerateCodeAction.OnSaveClick -> {
                state.value.newGeneratedCode?.let { newGeneratedCode ->
                    viewModelScope.launch(coroutineDispatcherProvider.mainImmediate) {
                        _state.update { it.copy(isSaveLoading = true) }
                        repository.saveCode(newGeneratedCode)
                        _eventChannel.send(GenerateCodeEvent.SaveSuccess)
                        _state.update { it.copy(isGenerateLoading = false) }
                    }
                }
            }
            // We navigate directly in our UI
            is GenerateCodeAction.OnBackClick -> Unit
        }
    }
}