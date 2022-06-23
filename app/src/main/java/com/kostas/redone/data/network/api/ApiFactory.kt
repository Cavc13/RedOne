package com.kostas.redone.data.network.api

import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object ApiFactory {

    private const val BASE_URL = "http://cbr.ru/scripts/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(TikXmlConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}