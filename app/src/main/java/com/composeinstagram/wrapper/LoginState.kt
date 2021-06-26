package com.composeinstagram.wrapper

sealed class LoginState {
    object Loading : LoginState()
    object Success : LoginState()
    object Fail : LoginState()
}

sealed class ActionState<out T> {
    object Loading : ActionState<Nothing>()
    data class Success<T>(val value: T) : ActionState<T>()
    data class Fail(val message: Throwable? = null) : ActionState<Nothing>()
}