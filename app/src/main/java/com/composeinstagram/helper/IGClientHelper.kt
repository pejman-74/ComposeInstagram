package com.composeinstagram.helper

import android.content.Context
import com.github.instagram4j.instagram4j.IGClient
import com.github.instagram4j.instagram4j.utils.IGUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.io.*

class IGClientHelper(context: Context) : IGClientHelperInterface {

    private val sessionFile = File(context.filesDir.absolutePath + "SessionFile.alt")
    private val cookFile = File(context.filesDir.absolutePath + "cookFile.alt")


    override suspend fun cachedIGClient(): IGClient? {
        return if (sessionFile.exists() && cookFile.exists()) {
            val deserializedCookieJar = deserialize(cookFile, SerializableCookieJar::class.java)

            runCatching {
                val okHttpClient = formOkHttpClient(deserializedCookieJar!!)
                IGClient.from(sessionFile.inputStream(), okHttpClient)
            }.onFailure { it.printStackTrace() }.getOrNull()
        } else
            null
    }

    override suspend fun login(userName: String, password: String): IGClient? = coroutineScope {
        val jar = SerializableCookieJar()
        val igClient = runCatching {
            IGClient.Builder().username(userName).password(password)
                .client(formOkHttpClient(jar)).login()

        }.onFailure { it.printStackTrace() }.getOrNull() ?: return@coroutineScope null

        launch {
            serialize(igClient, sessionFile)
            serialize(jar, cookFile)
        }

        igClient
    }

    private fun formOkHttpClient(jar: SerializableCookieJar): OkHttpClient {
        return IGUtils.defaultHttpClientBuilder().cookieJar(jar).build()
    }

    private suspend fun serialize(o: Any, to: File) = withContext(Dispatchers.IO) {
        runCatching {
            val file = FileOutputStream(to)
            val out = ObjectOutputStream(file)
            out.writeObject(o)
            out.flush()
            out.close()
            file.close()
        }.onFailure { it.printStackTrace() }.getOrNull()
    }

    private suspend fun <T> deserialize(file: File, clazz: Class<T>): T? =
        withContext(Dispatchers.IO) {
            runCatching {
                val inputStream: InputStream = FileInputStream(file)
                val objectInputStream = ObjectInputStream(inputStream)
                clazz.cast(objectInputStream.readObject()).also {
                    inputStream.close()
                    objectInputStream.close()
                }
            }.onFailure { it.printStackTrace() }.getOrNull()
        }
}