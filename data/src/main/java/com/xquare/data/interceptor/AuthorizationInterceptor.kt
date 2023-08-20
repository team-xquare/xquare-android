package com.xquare.data.interceptor

import com.google.gson.Gson
import com.xquare.data.local.preference.AuthPreference
import com.xquare.data.remote.response.auth.TokenResponse
import com.xquare.data.remote.response.auth.toEntity
import com.xquare.domain.AppCookieManager
import com.xquare.domain.exception.NeedLoginException
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val authPreference: AuthPreference,
    private val appCookieManager: AppCookieManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val method = request.method
        val ignorePath = listOf(
            "/users/login"
        )
        if (ignorePath.contains(path)) return chain.proceed(request)
        else if (path == "/users" && method == "POST") return chain.proceed(request)
        else if (path.startsWith("/meal")) return chain.proceed(request)

        val expiredAt = runBlocking { authPreference.fetchAccessTokenExpireAt() }
        val currentTime = LocalDateTime.now(ZoneId.systemDefault())

        if (expiredAt.isBefore(currentTime)) {
            val client = OkHttpClient()
            val refreshToken = runBlocking { authPreference.fetchRefreshToken() }

            val tokenRefreshRequest = Request.Builder()
                .url("https://api.xquare.app/users/login")
                .put("".toRequestBody("application/json".toMediaTypeOrNull()))
                .addHeader("Refresh-Token", "Bearer $refreshToken")
                .build()

            try {
                val response = client.newCall(tokenRefreshRequest).execute()

                if (response.isSuccessful) {
                    val token = Gson().fromJson(
                        response.body!!.string(),
                        TokenResponse::class.java
                    )
                    appCookieManager.writeToken(token.toEntity())
                    runBlocking {
                        authPreference.saveAccessToken(token.accessToken)
                        authPreference.saveRefreshToken(token.refreshToken)
                        authPreference.saveAccessTokenExpireAt(LocalDateTime.parse(token.accessTokenExpireAt))
                    }
                } else throw NeedLoginException()
            } catch (e: NeedLoginException) {
                runBlocking {
                    authPreference.saveAccessToken("")
                    authPreference.saveRefreshToken("")
                    authPreference.saveAccessTokenExpireAt(LocalDateTime.MIN)
                }
            }
        }


        val accessToken = runBlocking { authPreference.fetchAccessToken() }
        return chain.proceed(
            request.newBuilder().addHeader(
                "Authorization",
                "Bearer $accessToken"
            ).build()
        )
    }
}