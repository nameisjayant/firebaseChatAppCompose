package com.nameisjayant.chatapp.feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nameisjayant.chatapp.LocalNavigator
import com.nameisjayant.chatapp.feature.main.ui.screens.MainScreen
import com.nameisjayant.chatapp.feature.register.ui.screens.LoginScreen
import com.nameisjayant.chatapp.feature.register.ui.screens.RegisterScreen
import com.nameisjayant.chatapp.feature.register.ui.screens.SplashScreen


@Composable
fun AppNavigation(
    navHostController: NavHostController
) {
    CompositionLocalProvider(
        LocalNavigator provides navHostController
    ) {

        NavHost(navController = navHostController, startDestination = AppRoutes.Splash.route) {
            composable(AppRoutes.Login.route) {
                LoginScreen()
            }
            composable(AppRoutes.Register.route) {
                RegisterScreen()
            }
            composable(AppRoutes.Main.route) {
                MainScreen()
            }
            composable(AppRoutes.Splash.route){
                SplashScreen()
            }
        }
    }

}