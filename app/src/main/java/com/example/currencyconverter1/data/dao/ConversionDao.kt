package com.example.currencyconverter1.data.dao

import androidx.room.*
import com.example.currencyconverter1.data.entity.ConversionHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionDao {
    @Query("SELECT * FROM conversion_history WHERE user_id = :userId ORDER BY timestamp DESC")
    fun getConversionHistory(userId: Long): Flow<List<ConversionHistory>>

    @Insert
    suspend fun insertConversion(conversion: ConversionHistory): Long

    @Query("DELETE FROM conversion_history WHERE user_id = :userId")
    suspend fun clearUserHistory(userId: Long)

    @Query("DELETE FROM conversion_history WHERE id = :id AND user_id = :userId")
    suspend fun deleteConversion(id: Long, userId: Long)
}