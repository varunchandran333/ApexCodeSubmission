package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.remote.model.Users
import retrofit2.http.GET
import retrofit2.http.Query

interface APIs {
    @GET("api")
    suspend fun getUsers(): Users

    @GET("api")
    suspend fun getUsers(@Query("results") count: Int): Users
}
