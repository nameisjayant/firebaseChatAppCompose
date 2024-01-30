package com.nameisjayant.chatapp.feature.register.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nameisjayant.chatapp.LocalNavigator
import com.nameisjayant.chatapp.feature.navigation.AppRoutes
import com.nameisjayant.chatapp.feature.register.ui.viewmodel.AuthViewModel
import com.nameisjayant.chatapp.utils.PreferenceStore
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {

    val index by viewModel.getPref(PreferenceStore.index)
        .collectAsStateWithLifecycle(initialValue = "")
    val navHostController = LocalNavigator.current

    LaunchedEffect(key1 = index) {
        delay(2000)
        when (index) {
            "1" -> navHostController.navigate(AppRoutes.Main.route)
            else -> navHostController.navigate(AppRoutes.Login.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat App")
    }

}