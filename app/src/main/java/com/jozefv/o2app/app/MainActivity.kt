package com.jozefv.o2app.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.jozefv.o2app.app.presentation.navigation.NavigationRoot
import com.jozefv.o2app.core.presentation.theme.O2AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            O2AppTheme {
                NavigationRoot(navController)
            }
        }
    }
}
