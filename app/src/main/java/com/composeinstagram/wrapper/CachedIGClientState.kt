package com.composeinstagram.wrapper

sealed class CachedIGClientState {
    object Initial : CachedIGClientState()
    object Success : CachedIGClientState()
    object Fail : CachedIGClientState()
}