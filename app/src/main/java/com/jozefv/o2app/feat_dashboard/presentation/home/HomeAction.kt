package com.jozefv.o2app.feat_dashboard.presentation.home

sealed interface HomeAction {
    data object OnInfoClick: HomeAction
    data object OnGenerateClick : HomeAction
    data object OnActivateClick : HomeAction
}