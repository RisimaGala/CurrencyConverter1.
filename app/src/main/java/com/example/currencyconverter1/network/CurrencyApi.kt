
package com.example.currencyconverter1.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("base") baseCurrency: String,
        @Query("apikey") apiKey: String = "fca_live_gr44zril1pr8rMPViwQclnFQZyptbsIwJRjaDAjF"
    ): ExchangeRatesResponse
}
