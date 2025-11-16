package com.example.currencyconverter1.network

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("base")
    val baseCurrency: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("rates")
    val rates: Map<String, Double>
)