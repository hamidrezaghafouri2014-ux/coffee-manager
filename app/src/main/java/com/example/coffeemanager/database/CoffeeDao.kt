package com.example.coffeemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {
    @Insert
    suspend fun insert(log: CoffeeLog)

    @Query("SELECT * FROM coffee_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<CoffeeLog>>

    @Query("SELECT * FROM coffee_logs WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    fun getTodayLogs(startOfDay: Long): Flow<List<CoffeeLog>>
}
