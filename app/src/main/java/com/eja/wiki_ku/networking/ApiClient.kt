package com.eja.wiki_ku.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ApiClient {

    fun getApiClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://hadi-api.herokuapp.com/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}