package com.composeinstagram.helper

import com.github.instagram4j.instagram4j.IGClient
import org.mockito.Mockito.mock


class FakeIGClientHelper : IGClientHelperInterface {

    var shouldCachedIGClientReturnNull = false
    var shouldLoginReturnNull = false

    override suspend fun cachedIGClient(): IGClient? {
        return if (shouldCachedIGClientReturnNull) null else mockIGClient()
    }

    override suspend fun login(userName: String, password: String): IGClient? {
        return if (shouldLoginReturnNull) null else mockIGClient()
    }

    private fun mockIGClient(): IGClient {
        return mock(IGClient::class.java)
    }
}