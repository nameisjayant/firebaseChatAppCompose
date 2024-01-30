package com.nameisjayant.chatapp.feature.register.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nameisjayant.chatapp.LocalNavigator
import com.nameisjayant.chatapp.R
import com.nameisjayant.chatapp.components.ButtonComponent
import com.nameisjayant.chatapp.components.HavAccountComponent
import com.nameisjayant.chatapp.components.HorizontalDivider
import com.nameisjayant.chatapp.components.IconButtonComponent
import com.nameisjayant.chatapp.components.IconComponent
import com.nameisjayant.chatapp.components.SpacerHeight
import com.nameisjayant.chatapp.components.SpacerWidth
import com.nameisjayant.chatapp.components.TextFieldComponent
import com.nameisjayant.chatapp.data.model.AuthModel
import com.nameisjayant.chatapp.feature.navigation.AppRoutes
import com.nameisjayant.chatapp.feature.register.ui.viewmodel.AuthEvent
import com.nameisjayant.chatapp.feature.register.ui.viewmodel.AuthViewModel
import com.nameisjayant.chatapp.utils.PreferenceStore
import com.nameisjayant.chatapp.utils.ResultState
import com.nameisjayant.chatapp.utils.SOMETHING_WENT_WRONG
import com.nameisjayant.chatapp.utils.getActivity
import com.nameisjayant.chatapp.utils.navigateToWithPopping
import com.nameisjayant.chatapp.utils.showMsg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordShow by remember { mutableStateOf(false) }
    val navHostController = LocalNavigator.current
    val emailValidation by viewModel.emailValidation.collectAsStateWithLifecycle()
    val passwordValidation by viewModel.passwordValidation.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current.getActivity()!!
    val userId by viewModel.getPref(PreferenceStore.userId)
        .collectAsStateWithLifecycle(initialValue = "")
    val isCompleted by remember {
        derivedStateOf {
            email.trim().isNotEmpty() && password.trim().isNotEmpty() && emailValidation.isEmpty()
                    && passwordValidation.isEmpty() && name.trim()
                .isNotEmpty()
        }
    }

    LaunchedEffect(key1 = email) {
        viewModel.checkEmailValidation(email.trim())
    }
    LaunchedEffect(key1 = password) {
        viewModel.checkPasswordValidation(password.trim())
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            SpacerHeight(20.dp)
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.headlineLarge.copy(
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
            if (emailValidation.isNotEmpty() && email.isNotEmpty()) {
                SpacerHeight()
                Text(
                    text = emailValidation,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Red
                    ),
                )
            }
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
            if (passwordValidation.isNotEmpty() && password.isNotEmpty()) {
                SpacerHeight()
                Text(
                    text = passwordValidation,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Red
                    ),
                )
            }
            SpacerHeight(24.dp)
            ButtonComponent(content = {
                if (isLoading)
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(26.dp))
                else
                    Text(
                        text = stringResource(id = R.string.register),
                        style = LocalTextStyle.current.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = if (isCompleted) Color.White else Color.Gray
                        )
                    )
            }, background = if (isCompleted) Color.Black else Color.LightGray) {
                if (isCompleted)
                    viewModel.onEvent(
                        AuthEvent.RegisterWithEmailPasswordEvent(
                            AuthModel(
                                name, email, password
                            )
                        )
                    )
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
            SpacerHeight(40.dp)
            ButtonComponent(content = {
                Row {
                    IconComponent(icon = R.drawable.img, modifier = Modifier.size(16.dp))
                }
                SpacerWidth()
                Text(
                    text = stringResource(R.string.signup_with_google),
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }) {

            }
            SpacerHeight(20.dp)
            HavAccountComponent(
                text1 = stringResource(R.string.already_have_an_account),
                text2 = stringResource(id = R.string.login),
                onClick = {
                    navigateToWithPopping(navHostController, AppRoutes.Login.route)

                }
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.registerEmailPasswordEventFlow.collectLatest {
            isLoading = when (it) {
                is ResultState.Success -> {
                    delay(3000L)
                    viewModel.onEvent(
                        AuthEvent.AddUserDetailEventFlow(
                            AuthModel(
                                name, email, password
                            ),
                            userId
                        )
                    )
                    true
                }

                is ResultState.Failure -> {
                    context.showMsg(it.msg.message ?: SOMETHING_WENT_WRONG)
                    false
                }

                ResultState.Loading -> true

            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.addUserDetailEventFlow.collectLatest {
            isLoading = when (it) {
                is ResultState.Success -> {
                    context.showMsg("Account created!")
                    false
                }

                is ResultState.Failure -> {
                    context.showMsg(it.msg.message ?: SOMETHING_WENT_WRONG)
                    false
                }

                ResultState.Loading -> true

            }
        }
    }
}