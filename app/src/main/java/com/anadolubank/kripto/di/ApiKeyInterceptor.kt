package com.anadolubank.kripto.di

import android.content.Context
import com.anadolubank.kripto.R
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val ctx: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val url = req.url.newBuilder()
            .addQueryParameter("apikey", ctx.getString(R.string.API_KEY))
            .build()
        return chain.proceed(req.newBuilder().url(url).build())
    }
}