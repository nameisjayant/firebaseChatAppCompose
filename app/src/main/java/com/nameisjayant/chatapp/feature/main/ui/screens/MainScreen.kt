package com.nameisjayant.chatapp.feature.main.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nameisjayant.chatapp.LocalNavigator
import com.nameisjayant.chatapp.components.SpacerHeight
import com.nameisjayant.chatapp.components.SpacerWidth
import com.nameisjayant.chatapp.data.model.ProfileModel
import com.nameisjayant.chatapp.feature.main.ui.viewmodel.MainEvent
import com.nameisjayant.chatapp.feature.main.ui.viewmodel.MainViewModel
import com.nameisjayant.chatapp.feature.navigation.AppRoutes
import com.nameisjayant.chatapp.utils.PreferenceStore


@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    val userList by viewModel.userListEventFlow.collectAsStateWithLifecycle()
    val userId by viewModel.getPref(PreferenceStore.userId)
        .collectAsStateWithLifecycle(initialValue = "")
    val userProfile by viewModel.userProfileEventFlow.collectAsStateWithLifecycle()
    val navHostController = LocalNavigator.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        if (userProfile.data != null)
            Text(
                text = "Hi,${userProfile.data?.authModel?.name}",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.W600
                )
            )
        if (userList.data.size > 1)
            LazyColumn {
                item {
                    SpacerHeight(20.dp)
                }
                items(userList.data, key = { it.key ?: it.hashCode() }) {
                    if (it.key != userId)
                        UserRow(data = it) {
                            viewModel.setUserData(it)
                            navHostController.navigate(AppRoutes.Chat.route)
                        }
                }
                item {
                    SpacerHeight(16.dp)
                }
            }
    }
    LaunchedEffect(userId) {
        viewModel.onEvent(MainEvent.UserProfileEvent(userId))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(MainEvent.UserListEvent)

    }
}

@Composable
fun UserRow(
    modifier: Modifier = Modifier,
    data: ProfileModel,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Spacer(
            modifier = Modifier
                .background(Color.Gray, CircleShape)
                .size(50.dp)
        )
        SpacerWidth()
        Text(
            text = data.authModel?.name ?: "",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}