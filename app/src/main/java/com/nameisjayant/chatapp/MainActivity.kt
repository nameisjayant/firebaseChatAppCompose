package com.nameisjayant.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.nameisjayant.chatapp.feature.navigation.AppNavigation
import com.nameisjayant.chatapp.ui.theme.GmrTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            GmrTheme {
                AppNavigation(navHostController = navHostController)
            }
        }
    }
}

