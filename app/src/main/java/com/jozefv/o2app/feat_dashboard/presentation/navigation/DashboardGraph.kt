package com.jozefv.o2app.feat_dashboard.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jozefv.o2app.feat_dashboard.presentation.activate_code.ActivateCodeScreenRoot
import com.jozefv.o2app.feat_dashboard.presentation.generate_code.GenerateCodeScreenRoot
import com.jozefv.o2app.feat_dashboard.presentation.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object DashboardGraph {
    @Serializable
    data object Home

    @Serializable
    data object GenerateCode

    @Serializable
    data object ActivateCode
}

fun NavGraphBuilder.dashboardGraph(
    navHostController: NavHostController,
    onInfoClick: () -> Unit
) {
    navigation<DashboardGraph>(startDestination = DashboardGraph.Home) {
        composable<DashboardGraph.Home> {
            HomeScreenRoot(
                onInfoClick = {
                    onInfoClick()
                },
                onGenerateClick = {
                    navHostController.navigate(DashboardGraph.GenerateCode)
                },
                onActivateClick = {
                    navHostController.navigate(DashboardGraph.ActivateCode)
                }
            )
        }
        composable<DashboardGraph.GenerateCode> {
            GenerateCodeScreenRoot(
                onSaveSuccess = {
                    navHostController.navigateUp()
                },
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<DashboardGraph.ActivateCode> {
            ActivateCodeScreenRoot(
                onActivationSuccess = {
                    navHostController.navigateUp()
                },
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}