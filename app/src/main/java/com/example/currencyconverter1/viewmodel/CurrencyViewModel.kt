package com.example.currencyconverter1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val _ratesState = MutableStateFlow<RatesState>(RatesState.Idle)
    val ratesState: StateFlow<RatesState> = _ratesState.asStateFlow()

    private val _conversionState = MutableStateFlow<ConversionState>(ConversionState.Idle)
    val conversionState: StateFlow<ConversionState> = _conversionState.asStateFlow()

    private val _currencies = MutableStateFlow(listOf("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "ZAR"))
    val currencies: StateFlow<List<String>> = _currencies.asStateFlow()

    private val _apiStatus = MutableStateFlow<ApiStatus>(ApiStatus.Connected)
    val apiStatus: StateFlow<ApiStatus> = _apiStatus.asStateFlow()

    // Mock exchange rates data
    private val mockRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.86,
        "GBP" to 0.76,
        "JPY" to 154.04,
        "CAD" to 1.40,
        "AUD" to 1.53,
        "ZAR" to 17.13
    )

    fun getExchangeRates(baseCurrency: String = "USD") {
        viewModelScope.launch {
            _ratesState.value = RatesState.Loading
            // Simulate API delay
            kotlinx.coroutines.delay(1000)
            _ratesState.value = RatesState.Success(mockRates)
        }
    }

    fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String) {
        viewModelScope.launch {
            _conversionState.value = ConversionState.Loading
            try {
                // Simple conversion using mock rates
                val fromRate = mockRates[fromCurrency] ?: 1.0
                val toRate = mockRates[toCurrency] ?: 1.0

                val amountInUSD = amount / fromRate
                val convertedAmount = amountInUSD * toRate

                _conversionState.value = ConversionState.Success(convertedAmount)
            } catch (e: Exception) {
                _conversionState.value = ConversionState.Error("Conversion failed: ${e.message}")
            }
        }
    }

    fun refreshData() {
        getExchangeRates("USD")
    }

    sealed class RatesState {
        object Idle : RatesState()
        object Loading : RatesState()
        data class Success(val rates: Map<String, Double>) : RatesState()
        data class Error(val message: String) : RatesState()
    }

    sealed class ConversionState {
        object Idle : ConversionState()
        object Loading : ConversionState()
        data class Success(val amount: Double) : ConversionState()
        data class Error(val message: String) : ConversionState()
    }

    sealed class ApiStatus {
        object Checking : ApiStatus()
        object Connected : ApiStatus()
        object Fallback : ApiStatus()
    }
}