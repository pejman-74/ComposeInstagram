package com.composeinstagram.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.composeinstagram.helper.IGClientHelperInterface
import com.composeinstagram.wrapper.CachedIGClientState
import com.composeinstagram.wrapper.LoginState
import com.github.instagram4j.instagram4j.IGClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    private val igClientHelper: IGClientHelperInterface,
    @Named("MainDispatcher") mainDispatcher: CoroutineDispatcher,
    @Named("IODispatcher") ioDispatcher: CoroutineDispatcher
) : BaseViewModel(mainDispatcher, ioDispatcher) {

    var cachedIGClientState by mutableStateOf<CachedIGClientState>(CachedIGClientState.Initial)
    var loginState by mutableStateOf<LoginState>(LoginState.Initial)

    /**
     * sets from [init] or [login]
     * */
    lateinit var currentIGClient: IGClient

    init {
        /**
         * When the app launched get the previous session igClient if exist.
         * */
        doInIO {

            igClientHelper.cachedIGClient().let { igClient ->
                cachedIGClientState = if (igClient == null) {
                    CachedIGClientState.Fail
                } else {
                    currentIGClient = igClient
                    CachedIGClientState.Success
                }
            }

        }
    }

    fun login(userName: String, password: String) = doInIO {
        igClientHelper.login(userName, password).let { igClient ->
            loginState = if (igClient == null) {
                LoginState.Fail
            } else {
                currentIGClient = igClient
                LoginState.Success
            }
        }
    }


}

