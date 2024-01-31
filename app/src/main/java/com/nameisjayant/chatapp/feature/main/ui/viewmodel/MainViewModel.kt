package com.nameisjayant.chatapp.feature.main.ui.viewmodel

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.chatapp.data.model.ProfileModel
import com.nameisjayant.chatapp.data.repository.AppRepository
import com.nameisjayant.chatapp.utils.PreferenceStore
import com.nameisjayant.chatapp.utils.SOMETHING_WENT_WRONG
import com.nameisjayant.chatapp.utils.doOnFailure
import com.nameisjayant.chatapp.utils.doOnLoading
import com.nameisjayant.chatapp.utils.doOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository,
    private val preferenceStore: PreferenceStore
) : ViewModel() {

    private val _userListEventFlow: MutableStateFlow<ComposeUi<ProfileModel>> = MutableStateFlow(
        ComposeUi()
    )
    var userListEventFlow = _userListEventFlow.asStateFlow()
        private set

    private val _userProfileEventFlow: MutableStateFlow<ComposeObjectUi<ProfileModel>> =
        MutableStateFlow(
            ComposeObjectUi()
        )
    var userProfileEventFlow = _userProfileEventFlow.asStateFlow()
        private set

    private val _userData:MutableStateFlow<ProfileModel?> = MutableStateFlow(null)
    var userData = _userData.asStateFlow()
        private set

    fun setUserData(data:ProfileModel){
        _userData.value = data
    }

    fun onEvent(event: MainEvent) = viewModelScope.launch {
        when (event) {
            MainEvent.UserListEvent -> {
                repository.getUserList()
                    .doOnLoading {
                        _userListEventFlow.value = ComposeUi(
                            isLoading = true
                        )
                    }
                    .doOnFailure {
                        _userListEventFlow.value = ComposeUi(
                            error = it?.message ?: SOMETHING_WENT_WRONG
                        )
                    }
                    .doOnSuccess {
                        _userListEventFlow.value = ComposeUi(
                            data = it
                        )
                    }
                    .collect()
            }

            is MainEvent.UserProfileEvent -> {
                repository.getUserProfile(event.userId)
                    .doOnLoading {
                        _userProfileEventFlow.value = ComposeObjectUi(
                            isLoading = true
                        )
                    }
                    .doOnFailure {
                        _userProfileEventFlow.value = ComposeObjectUi(
                            error = it?.message ?: SOMETHING_WENT_WRONG
                        )
                    }
                    .doOnSuccess {
                        _userProfileEventFlow.value = ComposeObjectUi(
                            data = it
                        )
                    }
                    .collect()
            }
        }
    }

    fun setPref(key: Preferences.Key<String>, value: String) = viewModelScope.launch {
        preferenceStore.setPref(key, value)
    }

    fun getPref(key: Preferences.Key<String>) = preferenceStore.getPref(key)
    fun clearPrefs() = viewModelScope.launch {
        preferenceStore.clearDataStore()
    }
}

data class ComposeUi<T>(
    val data: List<T> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)

data class ComposeObjectUi<T>(
    val data: T? = null,
    val error: String = "",
    val isLoading: Boolean = false
)

sealed class MainEvent {
    data object UserListEvent : MainEvent()
    data class UserProfileEvent(val userId: String) : MainEvent()
}