package com.example.currencyconverter1.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.currencyconverter1.data.entity.User

class PrefManager private constructor(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "CurrencyConverterPrefs"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_USER_COUNTRY = "user_country"
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_DEFAULT_CURRENCY = "default_currency"

        @Volatile
        private var INSTANCE: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PrefManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    fun saveUser(userId: Long, email: String, name: String, phone: String = "", country: String = "") {
        with(sharedPreferences.edit()) {
            putLong(KEY_USER_ID, userId)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_PHONE, phone)
            putString(KEY_USER_COUNTRY, country)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun saveSettings(notificationsEnabled: Boolean, darkMode: Boolean, defaultCurrency: String) {
        with(sharedPreferences.edit()) {
            putBoolean(KEY_NOTIFICATIONS_ENABLED, notificationsEnabled)
            putBoolean(KEY_DARK_MODE, darkMode)
            putString(KEY_DEFAULT_CURRENCY, defaultCurrency)
            apply()
        }
    }

    fun getNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)
    }

    fun getDarkMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }

    fun getDefaultCurrency(): String {
        return sharedPreferences.getString(KEY_DEFAULT_CURRENCY, "USD") ?: "USD"
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getCurrentUserId(): Long {
        return sharedPreferences.getLong(KEY_USER_ID, -1L)
    }

    fun getCurrentUser(): User {
        return User(
            id = getCurrentUserId(),
            email = sharedPreferences.getString(KEY_USER_EMAIL, "") ?: "",
            name = sharedPreferences.getString(KEY_USER_NAME, "") ?: "",
            password = "",
            phone = sharedPreferences.getString(KEY_USER_PHONE, "") ?: "",
            country = sharedPreferences.getString(KEY_USER_COUNTRY, "") ?: "",
            notificationsEnabled = getNotificationsEnabled(),
            darkMode = getDarkMode(),
            defaultCurrency = getDefaultCurrency(),
            createdAt = System.currentTimeMillis()
        )
    }

    fun clearUser() {
        with(sharedPreferences.edit()) {
            remove(KEY_USER_ID)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_NAME)
            remove(KEY_USER_PHONE)
            remove(KEY_USER_COUNTRY)
            putBoolean(KEY_IS_LOGGED_IN, false)
            apply()
        }
    }
}