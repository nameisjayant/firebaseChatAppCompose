package com.nameisjayant.chatapp

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController


val LocalNavigator = staticCompositionLocalOf<NavHostController> {
    error("unable to get nav host controller")
}