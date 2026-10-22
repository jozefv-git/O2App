package com.jozefv.o2app.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jozefv.o2app.feat_dashboard.presentation.navigation.DashboardGraph
import com.jozefv.o2app.feat_dashboard.presentation.navigation.dashboardGraph
import com.jozefv.o2app.feat_info.presenation.navigation.InfoGraph
import com.jozefv.o2app.feat_info.presenation.navigation.infoGraph

@Composable
fun NavigationRoot(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = DashboardGraph
    ) {
        dashboardGraph(
            navHostController = navHostController,
            onInfoClick = {
                navHostController.navigate(InfoGraph)
            }
        )
        infoGraph(navHostController)
    }
}