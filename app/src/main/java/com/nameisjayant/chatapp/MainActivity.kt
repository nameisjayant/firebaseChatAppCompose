package com.nameisjayant.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nameisjayant.chatapp.feature.register.RegisterScreen
import com.nameisjayant.chatapp.ui.theme.GmrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GmrTheme {
                RegisterScreen()
            }
        }
    }
}
