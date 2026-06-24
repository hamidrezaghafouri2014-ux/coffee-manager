package com.example.coffeemanager.ui.state

data class HomeUiState(
    val lastCupTime: String = "No data",
    val todaysCaffeine: Int = 0,
    val energyLevel: Float = 0f,
    val energyStatus: String = "LOW ENERGY",
    val weeklyIntake: List<Float> = emptyList(),
    val isLoading: Boolean = false
)
