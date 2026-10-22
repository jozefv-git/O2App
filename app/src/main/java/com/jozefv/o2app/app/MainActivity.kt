package com.jozefv.o2app.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.jozefv.o2app.app.presentation.navigation.NavigationRoot
import com.jozefv.o2app.core.presentation.theme.O2AppTheme
// Restart the app from the scratch if the process death occurred
private var isFirstOnCreate = true
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFirstOnCreate && savedInstanceState != null) {
            isFirstOnCreate = false
            // Clear old app history instances
            val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()
            return
        }
        isFirstOnCreate = false
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            O2AppTheme {
                NavigationRoot(navController)
            }
        }
    }
}
