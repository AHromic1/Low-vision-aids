package com.example.lowvisionaidsbachelorthesis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExchangeRatesRetrofit {
    private const val BASE_URL = "https://open.er-api.com/v6/"

    val api: ExchangeRatesInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRatesInterface::class.java)
    }
}
