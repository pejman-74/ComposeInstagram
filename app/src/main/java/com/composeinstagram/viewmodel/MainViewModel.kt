package com.composeinstagram.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.composeinstagram.helper.IGClientHelperInterface
import com.composeinstagram.wrapper.CachedIGClientState
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


}

