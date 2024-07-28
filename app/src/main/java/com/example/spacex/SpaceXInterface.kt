package com.example.spacex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface SpaceXInterface {
    @GET("launches")
    fun getData(): Call<List<SpaceXResponse?>?>?
}