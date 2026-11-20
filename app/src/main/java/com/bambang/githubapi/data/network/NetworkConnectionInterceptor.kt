package com.bambang.githubapi.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            @Suppress("DEPRECATION")
            cm.activeNetworkInfo?.isConnected == true
        }
    }
}

class NoConnectivityException : IOException("Tidak ada koneksi internet")
