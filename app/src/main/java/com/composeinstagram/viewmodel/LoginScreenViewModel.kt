package com.composeinstagram.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.composeinstagram.helper.IGClientHelperInterface
import com.composeinstagram.wrapper.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val igClientHelper: IGClientHelperInterface,
    @Named("MainDispatcher") mainDispatcher: CoroutineDispatcher,
    @Named("IODispatcher") ioDispatcher: CoroutineDispatcher
) : BaseViewModel(mainDispatcher, ioDispatcher) {

    var userNameTextState by mutableStateOf("")
    var passwordTextState by mutableStateOf("")
    var lastPasswordLength by mutableStateOf(0)

    private val loginStateChanel =
        Channel<LoginState>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val loginState = loginStateChanel.receiveAsFlow()

    fun login(userName: String, password: String) = doInIO {
        loginStateChanel.send(LoginState.Loading)
        igClientHelper.login(userName, password).let { igClient ->
            val state = if (igClient == null) {
                LoginState.Fail
            } else {
                currentIGClient = igClient
                LoginState.Success
            }
            loginStateChanel.send(state)
        }
    }

}