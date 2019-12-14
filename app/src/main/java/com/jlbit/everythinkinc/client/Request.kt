package com.jlbit.everythinkinc.client

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Request(context: Context) {
    val urlApi = "https://randomuser.me/"

    private val cacheSize = (10 * 1024 * 1024).toLong()
    private val myCache = Cache(context.cacheDir, cacheSize)

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()

            request = if (hasNetwork(context)!!) {
                request.newBuilder().
                    header("Cache-Control", "public, max-age=${10}").
                    build()
            }else {
                request.newBuilder().
                    header("Cache-Control", "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}").
                    build()
            }

            chain.proceed(request)
        }
        .build()

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected) isConnected = true

        return isConnected
    }

    fun retrofit(): API {
        val retrofit = Retrofit.Builder()
            .baseUrl(urlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(API::class.java)
    }
}