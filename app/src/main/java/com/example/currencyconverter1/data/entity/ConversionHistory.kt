package com.example.currencyconverter1.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "conversion_history")
data class ConversionHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "from_currency")
    val fromCurrency: String,

    @ColumnInfo(name = "to_currency")
    val toCurrency: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "converted_amount")
    val convertedAmount: Double,

    @ColumnInfo(name = "rate")
    val rate: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis()
)