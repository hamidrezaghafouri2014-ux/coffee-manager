package com.example.coffeemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeemanager.database.CoffeeDao
import com.example.coffeemanager.database.CoffeeLog
import com.example.coffeemanager.ui.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coffeeDao: CoffeeDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val caffeineGoal = 195f

    init {
        observeTodayLogs()
        observeWeeklyLogs()
    }

    private fun observeTodayLogs() {
        viewModelScope.launch {
            coffeeDao.getAllLogs().collect { logs ->
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val startOfDay = calendar.timeInMillis
                
                val todayLogs = logs.filter { it.timestamp >= startOfDay }
                val totalCaffeine = todayLogs.sumOf { it.caffeineAmount }
                val lastCup = if (todayLogs.isNotEmpty()) {
                    SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(todayLogs.first().timestamp))
                } else {
                    "No data"
                }

                // Calculate energy based on today's total
                val energyLevel = (totalCaffeine / caffeineGoal).coerceIn(0f, 1f)
                val energyStatus = when {
                    energyLevel >= 0.8f -> "ENERGY FULL!"
                    energyLevel >= 0.5f -> "CHARGED UP"
                    energyLevel >= 0.2f -> "AWAKE"
                    else -> "STILL TIRED"
                }

                _uiState.value = _uiState.value.copy(
                    lastCupTime = lastCup,
                    todaysCaffeine = totalCaffeine,
                    energyLevel = energyLevel,
                    energyStatus = energyStatus
                )
            }
        }
    }

    private fun observeWeeklyLogs() {
        viewModelScope.launch {
            coffeeDao.getAllLogs().collect { allLogs ->
                if (allLogs.isEmpty()) {
                    _uiState.value = _uiState.value.copy(weeklyIntake = emptyList())
                    return@collect
                }

                val weeklyData = MutableList(7) { 0f }
                val calendar = Calendar.getInstance()
                
                // Group by day of week (M=0, ..., S=6)
                // Note: Calendar.DAY_OF_WEEK returns Sunday=1, Monday=2, ...
                val startOfCurrentWeek = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    // Find Monday of the current week
                    val daysFromMonday = (get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY + 7) % 7
                    add(Calendar.DAY_OF_YEAR, -daysFromMonday)
                }.timeInMillis

                allLogs.filter {
                    it.timestamp >= startOfCurrentWeek
                }.forEach { log ->
                    calendar.timeInMillis = log.timestamp
                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                    val index = when (dayOfWeek) {
                        Calendar.MONDAY -> 0
                        Calendar.TUESDAY -> 1
                        Calendar.WEDNESDAY -> 2
                        Calendar.THURSDAY -> 3
                        Calendar.FRIDAY -> 4
                        Calendar.SATURDAY -> 5
                        Calendar.SUNDAY -> 6
                        else -> 0
                    }
                    weeklyData[index] += log.caffeineAmount.toFloat()
                }

                val normalizedWeekly = weeklyData.map { it / caffeineGoal }
                _uiState.value = _uiState.value.copy(weeklyIntake = normalizedWeekly)
            }
        }
    }

    fun logCoffee(beanType: String, shots: Int, feeling: String) {
        viewModelScope.launch {
            val caffeinePerShot = when (beanType) {
                "Robusta" -> 100
                else -> 65
            }
            val caffeineAmount = shots * caffeinePerShot
            
            val log = CoffeeLog(
                beanType = beanType,
                shots = shots,
                caffeineAmount = caffeineAmount,
                feeling = feeling
            )
            coffeeDao.insert(log)
        }
    }
}
