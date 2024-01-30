package com.nameisjayant.chatapp.feature.navigation

sealed class AppRoutes(val route: String) {

    data object Splash : AppRoutes("/splash")
    data object Register : AppRoutes("/register")
    data object Login : AppRoutes("/login")
    data object Main : AppRoutes("/main")
}