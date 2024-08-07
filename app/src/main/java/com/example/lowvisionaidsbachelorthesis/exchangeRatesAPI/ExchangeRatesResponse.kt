package com.example.lowvisionaidsbachelorthesis.exchangeRatesAPI

data class ExchangeRatesResponse(
    val result: String,
    val provider: String,
    val documentation: String,
    val terms_of_use: String,
    val time_last_update_unix: Long,
    val time_last_update_utc: String,
    val time_next_update_unix: Long,
    val time_next_update_utc: String,
    val time_eol_unix: Long,
    val base_code: String,
    val rates: Map<String, Double>
)
