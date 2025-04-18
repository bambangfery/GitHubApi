package com.bambang.githubapi.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RateLimitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code() == 403) {
            val bodyString = response.peekBody(Long.MAX_VALUE).string()
            if (bodyString.contains("API rate limit exceeded")) {
                throw RateLimitExceededException("GitHub API rate limit exceeded.")
            }
        }

        return response
    }
}

class RateLimitExceededException(message: String) : IOException(message)