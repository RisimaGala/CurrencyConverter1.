package com.example.currencyconverter1.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyRepository {
    private val tag = "CurrencyRepository"

    // Static fallback rates - INCLUDING ZAR
    private val fallbackRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "GBP" to 0.73,
        "JPY" to 110.0,
        "CAD" to 1.25,
        "AUD" to 1.35,
        "ZAR" to 18.50
    )

    suspend fun getExchangeRates(baseCurrency: String = "USD"): Result<Map<String, Double>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(tag, "Using fallback rates for: $baseCurrency")
                // For now, return fallback rates - you can add API call later
                Result.success(fallbackRates)
            } catch (e: Exception) {
                Log.e(tag, "Error: ${e.message}")
                Result.success(fallbackRates)
            }
        }
    }

    suspend fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Result<Double> {
        return withContext(Dispatchers.IO) {
            try {
                if (fromCurrency == toCurrency) {
                    Result.success(amount)
                } else {
                    val fromRate = fallbackRates[fromCurrency] ?: 1.0
                    val toRate = fallbackRates[toCurrency] ?: 1.0
                    val convertedAmount = (amount / fromRate) * toRate
                    Result.success(convertedAmount)
                }
            } catch (e: Exception) {
                Log.e(tag, "Conversion error: ${e.message}")
                Result.success(amount) // Fallback to original amount
            }
        }
    }

    fun getSupportedCurrencies(): List<String> {
        return fallbackRates.keys.toList()
    }
}