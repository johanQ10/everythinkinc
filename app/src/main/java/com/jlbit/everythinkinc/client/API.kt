package com.jlbit.everythinkinc.client

import com.jlbit.everythinkinc.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface API {

    @GET("api")
    fun getUsers(): Call<Data?>

}