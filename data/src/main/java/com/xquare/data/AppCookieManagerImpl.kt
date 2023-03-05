package com.xquare.data

import android.content.Context
import android.webkit.CookieManager
import com.xquare.domain.AppCookieManager
import com.xquare.domain.entity.auth.TokenEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCookieManagerImpl @Inject constructor() : AppCookieManager {

    private val cookieManager = CookieManager.getInstance().apply { setAcceptCookie(true) }

    override fun writeToken(tokenEntity: TokenEntity) {
        removeCookie("accessToken")
        removeCookie("refreshToken")
        writeCookie("accessToken", tokenEntity.accessToken)
        writeCookie("refreshToken", tokenEntity.refreshToken)
        cookieManager.flush()
    }

    override fun removeToken() {
        removeCookie("accessToken")
        removeCookie("refreshToken")
        cookieManager.flush()
    }

    private fun removeCookie(key: String) {
        writeCookie(key, "''")
    }

    private fun writeCookie(key: String, value: String) {
        cookieManager.setCookie(DOMAIN, "$key=$value")
    }

    companion object {
        const val DOMAIN = "https://service.xquare.app"
    }
}