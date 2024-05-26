package com.example.lowvisionaidsbachelorthesis.exchangeRatesAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ExchangeRatesViewModel : ViewModel() {

    private val _exchangeRates = MutableLiveData<Map<String, Double>>()
    val exchangeRates: LiveData<Map<String, Double>> = _exchangeRates

    fun fetchExchangeRates(baseCurrency: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ExchangeRatesRetrofit.api.getLatestRates(baseCurrency)
                _exchangeRates.postValue(response.rates)
              /*  println("RESPONSE RESULT:")
                response.rates.forEach { (currency, rate) ->
                    println("$currency: $rate")
                }*/
            } catch (e: HttpException) {
                // Handle HTTP exceptions
            } catch (e: Exception) {
                // Handle other exceptions
            }
        }
    }
}
