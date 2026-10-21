package com.jozefv.o2app.feat_dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jozefv.o2app.feat_dashboard.presentation.generate_code.GenerateCodeScreenRoot
import com.jozefv.o2app.feat_dashboard.presentation.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object DashboardGraph {
    @Serializable
    data object Home

    @Serializable
    data object GenerateCode
}

fun NavGraphBuilder.dashboardGraph(navHostController: NavHostController) {
    navigation<DashboardGraph>(startDestination = DashboardGraph.Home) {
        composable<DashboardGraph.Home> {
            HomeScreenRoot(
                onGenerateClick = {
                    navHostController.navigate(DashboardGraph.GenerateCode)
                },
                onActivateClick = {}
            )
        }
        composable<DashboardGraph.GenerateCode> {
            GenerateCodeScreenRoot(
                onSaveSuccess = {},
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}