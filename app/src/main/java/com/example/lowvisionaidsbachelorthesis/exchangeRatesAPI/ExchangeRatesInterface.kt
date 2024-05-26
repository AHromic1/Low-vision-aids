package com.example.lowvisionaidsbachelorthesis.exchangeRatesAPI

import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRatesInterface {
    @GET("latest/{baseCurrency}")
    suspend fun getLatestRates(@Path("baseCurrency") baseCurrency: String): ExchangeRatesResponse
}
