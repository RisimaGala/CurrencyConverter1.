package com.example.currencyconverter1.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.currencyconverter1.data.dao.UserDao
import com.example.currencyconverter1.data.dao.ConversionDao
import com.example.currencyconverter1.data.entity.User
import com.example.currencyconverter1.data.entity.ConversionHistory

@Database(
    entities = [User::class, ConversionHistory::class],
    version = 2, // Incremented version
    exportSchema = false
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun conversionDao(): ConversionDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "currency_database"
                ).fallbackToDestructiveMigration() // Added for schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}