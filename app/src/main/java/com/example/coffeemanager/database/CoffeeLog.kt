package com.example.coffeemanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_logs")
data class CoffeeLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val beanType: String,
    val shots: Int,
    val caffeineAmount: Int,
    val feeling: String,
    val timestamp: Long = System.currentTimeMillis()
)
