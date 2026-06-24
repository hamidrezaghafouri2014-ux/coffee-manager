package com.example.coffeemanager.di

import android.content.Context
import androidx.room.Room
import com.example.coffeemanager.database.CoffeeDao
import com.example.coffeemanager.database.CoffeeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CoffeeDatabase {
        return Room.databaseBuilder(
            context,
            CoffeeDatabase::class.java,
            "coffee_database"
        ).build()
    }

    @Provides
    fun provideCoffeeDao(database: CoffeeDatabase): CoffeeDao {
        return database.coffeeDao()
    }
}
