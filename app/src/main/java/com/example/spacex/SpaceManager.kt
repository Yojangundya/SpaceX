package com.example.spacex

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpaceManager {
    var spaceResponse: List<SpaceXResponse?>? = null
    var selectedData: SpaceXResponse? = null
    fun showDetailScreen(c: Context?, item: SpaceXResponse?) {
        val intent = Intent(c, SpaceShuttleDetailScreenActivity::class.java)
        selectedData = item
        c?.startActivity(intent)
    }

     fun makeApiCall(callback: (List<SpaceXResponse?>?,Boolean)->Unit) {
        Log.d("test", "onResponse: 2")
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val call = retrofit.create(SpaceXInterface::class.java)
                .getData()
            call?.enqueue(object : Callback<List<SpaceXResponse?>?> {
                override fun onResponse(
                    call: Call<List<SpaceXResponse?>?>,
                    response: Response<List<SpaceXResponse?>?>
                ) {
                    val result = response.body()
                    callback.invoke(result,false)

                }

                override fun onFailure(call: Call<List<SpaceXResponse?>?>, t: Throwable) {
                    Log.d("test", "onResponse: $t")
                    callback.invoke(null,false)

                }
            })
        } catch (t: Throwable) {
            callback.invoke(null,false)
            Log.d("test", "onResponse: $t")
        }
    }
}