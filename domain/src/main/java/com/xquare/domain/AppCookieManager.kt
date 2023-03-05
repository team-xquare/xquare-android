package com.xquare.domain

import com.xquare.domain.entity.auth.TokenEntity

interface AppCookieManager {
    fun writeToken(tokenEntity: TokenEntity)

    fun removeToken()
}