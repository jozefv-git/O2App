package com.jozefv.o2app.feat_info.presenation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jozefv.o2app.feat_info.presenation.info.InfoScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object InfoGraph {
    @Serializable
    data object Info
}

fun NavGraphBuilder.infoGraph(navHostController: NavHostController) {
    navigation<InfoGraph>(startDestination = InfoGraph.Info) {
        composable<InfoGraph.Info> {
            InfoScreenRoot(
                onBackClick = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}

