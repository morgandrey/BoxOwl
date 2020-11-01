package com.example.boxowl.remote

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Andrey Morgunov on 01/11/2020.
 */

object RetrofitClient {
    private lateinit var retrofit: Retrofit

    fun getClient(baseUrl: String): Retrofit {
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit
    }
}