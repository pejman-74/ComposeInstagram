package com.composeinstagram.helper

import com.github.instagram4j.instagram4j.IGClient

interface IGClientHelperInterface {
    suspend fun cachedIGClient(): IGClient?
    suspend fun login(userName: String, password: String): IGClient?
}