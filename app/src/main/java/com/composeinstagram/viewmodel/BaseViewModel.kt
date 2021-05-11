package com.composeinstagram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val mainDispatcher: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    fun doInMain(action: suspend () -> Unit) {
        viewModelScope.launch(mainDispatcher) {
            action()
        }
    }

    fun doInIO(action: suspend () -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            action()
        }
    }
}