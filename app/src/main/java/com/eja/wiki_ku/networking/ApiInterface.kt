package com.eja.wiki_ku.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("wiki")
    fun getWiki(
        @Query("query")
        strKunciWiki: String
    ): Call<String>
}