package com.example.coffeemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoffeeLog::class], version = 1, exportSchema = false)
abstract class CoffeeDatabase : RoomDatabase() {
    abstract fun coffeeDao(): CoffeeDao
}
