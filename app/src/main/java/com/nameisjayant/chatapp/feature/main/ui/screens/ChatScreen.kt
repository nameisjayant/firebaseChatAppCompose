package com.nameisjayant.chatapp.feature.main.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nameisjayant.chatapp.LocalNavigator
import com.nameisjayant.chatapp.R
import com.nameisjayant.chatapp.components.IconButtonComponent
import com.nameisjayant.chatapp.components.TextFieldComponent
import com.nameisjayant.chatapp.feature.main.ui.viewmodel.MainViewModel
import com.nameisjayant.chatapp.utils.PreferenceStore


@Composable
fun ChatScreen(
    viewModel: MainViewModel
) {

    var message by remember { mutableStateOf("") }
    val navHostController = LocalNavigator.current
    val chatUser by viewModel.userData.collectAsStateWithLifecycle()
    val userId by viewModel.getPref(PreferenceStore.userId)
        .collectAsStateWithLifecycle(initialValue = "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16.dp
            )
    ) {
        Row {
            IconButtonComponent(imageVector = Icons.AutoMirrored.Filled.ArrowBack) {
                navHostController.navigateUp()
            }
            Text(
                text = chatUser?.authModel?.name ?: "", style = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W700,
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {}
        Row {
            TextFieldComponent(
                value = message,
                onValueChange = { message = it },
                placeholder = stringResource(R.string.message),
                modifier = Modifier.weight(1f)
            )
            IconButtonComponent(imageVector = Icons.AutoMirrored.Filled.Send) {

            }
        }
    }

}