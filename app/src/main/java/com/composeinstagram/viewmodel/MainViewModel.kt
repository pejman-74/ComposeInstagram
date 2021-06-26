package com.composeinstagram.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.composeinstagram.helper.IGClientHelperInterface
import com.composeinstagram.wrapper.ActionState
import com.composeinstagram.wrapper.CachedIGClientState
import com.composeinstagram.wrapper.LoginState
import com.github.instagram4j.instagram4j.IGClient
import com.github.instagram4j.instagram4j.models.feed.Reel
import com.github.instagram4j.instagram4j.responses.feed.FeedTimelineResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class MainViewModel @Inject constructor(
    private val igClientHelper: IGClientHelperInterface,
    @Named("MainDispatcher") mainDispatcher: CoroutineDispatcher,
    @Named("IODispatcher") ioDispatcher: CoroutineDispatcher
) : BaseViewModel(mainDispatcher, ioDispatcher) {


    var cachedIGClientState by mutableStateOf<CachedIGClientState>(CachedIGClientState.Initial)
    private val loginStateChanel =
        Channel<LoginState>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val loginState = loginStateChanel.receiveAsFlow()

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
                doInMain {
                    cachedIGClientState = if (igClient == null) {
                        CachedIGClientState.Fail
                    } else {
                        currentIGClient = igClient
                        CachedIGClientState.Success
                    }
                }

            }
        }

    }

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

    var feedStory by mutableStateOf<ActionState<List<Reel>>?>(null)

    fun feedStory() = doInIO {
        feedStory = ActionState.Loading
        runCatching {
            val tray = currentIGClient.actions.story().tray().get().tray
            feedStory = ActionState.Success(tray)
        }.onFailure {
            feedStory = ActionState.Fail(it)
        }

    }

    val feedPost: FeedTimelineResponse by lazy {
        currentIGClient.actions.timeline().feed().first()
    }


}

