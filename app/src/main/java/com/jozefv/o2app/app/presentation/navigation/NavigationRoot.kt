package com.jozefv.o2app.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jozefv.o2app.feat_dashboard.presentation.navigation.DashboardGraph
import com.jozefv.o2app.feat_dashboard.presentation.navigation.dashboardGraph

@Composable
fun NavigationRoot(
    navHostController: NavHostController
){
    NavHost(
        navHostController,
        startDestination = DashboardGraph
    ){
        dashboardGraph(navHostController)
    }
}