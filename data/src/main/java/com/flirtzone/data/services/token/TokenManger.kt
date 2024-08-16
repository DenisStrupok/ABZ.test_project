package com.flirtzone.data.services.token

import android.annotation.SuppressLint
import android.content.Context
import com.flirtzone.data.response.token.TokenResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TokenManger(
    context: Context
) {

    companion object {
        const val ACCESS_TOKEN_SHARED_PREF = "access token shared pref"
        const val ACCESS_TOKEN = "access token"
        const val ACCESS_TOKEN_TIME = "access token time"
    }

    private val tokenSP =
        context.getSharedPreferences(ACCESS_TOKEN_SHARED_PREF, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    fun saveAccessToken(tokenResponse: TokenResponse) {
        val edit = tokenSP.edit()
        edit.putString(ACCESS_TOKEN, tokenResponse.token)
        edit.putLong(ACCESS_TOKEN_TIME, System.currentTimeMillis())
        edit.apply()
    }

    fun getAccessToken(): String? {
        return tokenSP.getString(ACCESS_TOKEN, null)
    }

    fun isTokenValid(): Boolean {
        val tokenTime = tokenSP.getLong(ACCESS_TOKEN_TIME, 0)
        val currentTime = System.currentTimeMillis()
        val tokenValidDuration = 40 * 60 * 1000
        return (currentTime - tokenTime) < tokenValidDuration
    }
}