package com.composeinstagram.wrapper

sealed class LoginState {
    object Initial : LoginState()
    object Success : LoginState()
    object Fail : LoginState()
}