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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nameisjayant.chatapp.components.SpacerHeight
import com.nameisjayant.chatapp.components.SpacerWidth


@Composable
fun MainScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hi,Username", style = MaterialTheme.typography.headlineSmall.copy(
                color = Color.Black,
                fontWeight = FontWeight.W600
            )
        )
        LazyColumn {
            item {
                SpacerHeight(20.dp)
            }
            items(30) {
                UserRow {}
            }
        }
    }

}

@Composable
fun UserRow(
    modifier: Modifier = Modifier,
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
        Text(text = "Jayant", modifier = Modifier.align(Alignment.CenterVertically))
    }
}