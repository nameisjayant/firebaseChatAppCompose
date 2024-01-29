package com.nameisjayant.chatapp.feature.register

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nameisjayant.chatapp.R
import com.nameisjayant.chatapp.components.ButtonComponent
import com.nameisjayant.chatapp.components.HorizontalDivider
import com.nameisjayant.chatapp.components.IconButtonComponent
import com.nameisjayant.chatapp.components.SpacerHeight
import com.nameisjayant.chatapp.components.SpacerWidth
import com.nameisjayant.chatapp.components.TextFieldComponent


@Composable
fun RegisterScreen() {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordShow by remember { mutableStateOf(false) }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            SpacerHeight(20.dp)
            Text(
                text = "Register", style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.W700
                )
            )
            SpacerHeight(24.dp)
            TextFieldComponent(
                value = name,
                onValueChange = { name = it },
                placeholder = stringResource(R.string.enter_name),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            SpacerHeight(20.dp)
            TextFieldComponent(
                value = email,
                onValueChange = { email = it },
                placeholder = stringResource(R.string.enter_email),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            )
            SpacerHeight(20.dp)
            TextFieldComponent(
                value = password,
                onValueChange = { password = it },
                placeholder = stringResource(R.string.enter_password),
                isPasswordVisible = isPasswordShow,
                trailingIcon = {
                    IconButtonComponent(
                        imageVector = Icons.Default.RemoveRedEye,
                        tint = if (isPasswordShow) Color.Black else Color.LightGray
                    ) {
                        isPasswordShow = !isPasswordShow
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                )
            )
            SpacerHeight(24.dp)
            ButtonComponent(title = stringResource(R.string.register)) {

            }
            SpacerHeight(40.dp)
            Row {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(0.5f)
                        .align(Alignment.CenterVertically)
                )
                SpacerWidth()
                Text(text = "OR")
                SpacerWidth()
                HorizontalDivider(
                    modifier = Modifier
                        .weight(0.5f)
                        .align(Alignment.CenterVertically)
                )
            }
        }

    }

}