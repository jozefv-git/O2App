package com.jozefv.o2app.feat_dashboard.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class HomeViewModel: ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _eventChannel = Channel<HomeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun onAction(action: HomeAction){
        when(action){
            is HomeAction.OnActivateClick -> {
                
            }
            // We navigate directly in our UI
            is HomeAction.OnGenerateClick -> Unit
        }
    }
}