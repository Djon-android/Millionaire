package com.example.millionaire.presentation.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.millionaire.domain.usecases.SaveUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveUsernameUseCase: SaveUsernameUseCase
) : ViewModel() {

    private var _username = mutableStateOf("")
    val username: State<String> = _username

    fun changeUsername(username: String) {
        _username.value = username
    }

    fun saveUsername() {
        viewModelScope.launch {
            saveUsernameUseCase(username.value)
        }
    }
}