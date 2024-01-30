package com.nameisjayant.chatapp.feature.register.ui.viewmodel

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.chatapp.data.model.AuthModel
import com.nameisjayant.chatapp.data.repository.AppRepository
import com.nameisjayant.chatapp.utils.EMAIL_PATTERN
import com.nameisjayant.chatapp.utils.INVALID_EMAIL
import com.nameisjayant.chatapp.utils.PASSWORD_VALIDATION
import com.nameisjayant.chatapp.utils.PreferenceStore
import com.nameisjayant.chatapp.utils.ResultState
import com.nameisjayant.chatapp.utils.SOMETHING_WENT_WRONG
import com.nameisjayant.chatapp.utils.doOnFailure
import com.nameisjayant.chatapp.utils.doOnLoading
import com.nameisjayant.chatapp.utils.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AppRepository,
    private val preferenceStore: PreferenceStore
) : ViewModel() {

    private val _emailValidation: MutableStateFlow<String> = MutableStateFlow("")
    var emailValidation = _emailValidation.asStateFlow()
        private set
    private val _passwordValidation: MutableStateFlow<String> = MutableStateFlow("")
    var passwordValidation = _passwordValidation.asStateFlow()
        private set
    private val _registerEmailPasswordEventFlow: MutableSharedFlow<ResultState<String>> =
        MutableSharedFlow()
    var registerEmailPasswordEventFlow = _registerEmailPasswordEventFlow.asSharedFlow()
        private set

    private val _loginEmailPasswordEventFlow: MutableSharedFlow<ResultState<String>> =
        MutableSharedFlow()
    var loginEmailPasswordEventFlow = _loginEmailPasswordEventFlow.asSharedFlow()
        private set

    private val _addUserDetailEventFlow: MutableSharedFlow<ResultState<String>> =
        MutableSharedFlow()
    var addUserDetailEventFlow = _addUserDetailEventFlow.asSharedFlow()
        private set

    fun onEvent(event: AuthEvent) = viewModelScope.launch {
        when (event) {
            is AuthEvent.LoginWithEmailAndPasswordEvent -> {
                repository.loginWithEmailPassword(event.data)
                    .doOnLoading {
                        _loginEmailPasswordEventFlow.emit(
                            ResultState.Loading
                        )
                    }
                    .doOnFailure {
                        _loginEmailPasswordEventFlow.emit(
                            ResultState.Failure(it ?: Throwable(SOMETHING_WENT_WRONG))
                        )
                    }
                    .doOnSuccess {
                        _loginEmailPasswordEventFlow.emit(
                            ResultState.Success(it)
                        )
                    }
                    .collect()
            }

            is AuthEvent.RegisterWithEmailPasswordEvent -> {
                repository.registerAccountWithEmailPassword(event.data)
                    .doOnLoading {
                        _registerEmailPasswordEventFlow.emit(
                            ResultState.Loading
                        )
                    }
                    .doOnFailure {
                        _registerEmailPasswordEventFlow.emit(
                            ResultState.Failure(it ?: Throwable(SOMETHING_WENT_WRONG))
                        )
                    }
                    .doOnSuccess {
                        _registerEmailPasswordEventFlow.emit(
                            ResultState.Success(it)
                        )
                    }
                    .collect()
            }

            is AuthEvent.AddUserDetailEventFlow -> {
                repository.addUserDetail(event.data,event.userId)
                    .doOnLoading {
                        _addUserDetailEventFlow.emit(
                            ResultState.Loading
                        )
                    }
                    .doOnFailure {
                        _addUserDetailEventFlow.emit(
                            ResultState.Failure(it ?: Throwable(SOMETHING_WENT_WRONG))
                        )
                    }
                    .doOnSuccess {
                        _addUserDetailEventFlow.emit(
                            ResultState.Success(it)
                        )
                    }
                    .collect()
            }
        }
    }

    fun checkEmailValidation(email: String) {
        if (email.isEmpty())
            _emailValidation.value = ""
        else
            if (!Pattern.compile(
                    EMAIL_PATTERN
                ).matcher(email).matches()
            ) {
                _emailValidation.value = INVALID_EMAIL
            } else _emailValidation.value = ""
    }

    fun checkPasswordValidation(password: String) {
        if (password.isEmpty())
            _passwordValidation.value = ""
        else if (password.trim().length > 6)
            _passwordValidation.value = ""
        else
            _passwordValidation.value = PASSWORD_VALIDATION
    }

    fun setPref(key: Preferences.Key<String>, value: String) = viewModelScope.launch {
        preferenceStore.setPref(key, value)
    }

    fun getPref(key: Preferences.Key<String>) = preferenceStore.getPref(key)
    fun clearPrefs() = viewModelScope.launch {
        preferenceStore.clearDataStore()
    }

}

sealed class AuthEvent {

    data class RegisterWithEmailPasswordEvent(val data: AuthModel) : AuthEvent()
    data class LoginWithEmailAndPasswordEvent(val data: AuthModel) : AuthEvent()
    data class AddUserDetailEventFlow(val data: AuthModel,val userId:String) : AuthEvent()

}