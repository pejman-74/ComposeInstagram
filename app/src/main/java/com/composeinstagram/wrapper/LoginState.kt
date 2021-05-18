package com.composeinstagram.wrapper

sealed class LoginState {
    object Loading : LoginState()
    object Success : LoginState()
    object Fail : LoginState()
}