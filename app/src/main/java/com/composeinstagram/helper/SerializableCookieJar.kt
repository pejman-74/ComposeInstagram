package com.composeinstagram.helper

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.*

class SerializableCookieJar : CookieJar, Serializable {

    companion object {
        private const val serialVersionUID = -837498359387593793L
    }

    var map = HashMap<String, List<SerializableCookie>>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return map[url.host]?.map { it.cookie }?.toList() ?: emptyList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        map[url.host] = cookies.map {
            SerializableCookie(it)
        }
    }

    class SerializableCookie internal constructor(@field:Transient var cookie: Cookie) :
        Serializable {
        @Throws(IOException::class)
        private fun writeObject(out: ObjectOutputStream) {
            out.writeObject(cookie.name)
            out.writeObject(cookie.value)
            out.writeLong(if (cookie.persistent) cookie.expiresAt else NON_VALID_EXPIRES_AT)
            out.writeObject(cookie.domain)
            out.writeObject(cookie.path)
            out.writeBoolean(cookie.secure)
            out.writeBoolean(cookie.httpOnly)
            out.writeBoolean(cookie.hostOnly)
        }

        @Throws(IOException::class, ClassNotFoundException::class)
        private fun readObject(objectInputStream: ObjectInputStream) {
            val builder = Cookie.Builder()
            builder.name(objectInputStream.readObject() as String)
            builder.value(objectInputStream.readObject() as String)
            val expiresAt = objectInputStream.readLong()
            if (expiresAt != NON_VALID_EXPIRES_AT) {
                builder.expiresAt(expiresAt)
            }
            val domain = objectInputStream.readObject() as String
            builder.domain(domain)
            builder.path(objectInputStream.readObject() as String)
            if (objectInputStream.readBoolean()) builder.secure()
            if (objectInputStream.readBoolean()) builder.httpOnly()
            if (objectInputStream.readBoolean()) builder.hostOnlyDomain(domain)
            cookie = builder.build()
        }

        companion object {
            private const val serialVersionUID = -8594045714036645534L
            private const val NON_VALID_EXPIRES_AT = -1L
        }
    }
}