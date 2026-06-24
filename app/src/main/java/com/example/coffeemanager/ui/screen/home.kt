package com.example.coffeemanager.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffeemanager.ui.theme.Border
import com.example.coffeemanager.ui.theme.CoffeeBackground
import com.example.coffeemanager.ui.theme.CoffeePrimaryAccent
import com.example.coffeemanager.ui.theme.CoffeeSurface
import com.example.coffeemanager.ui.theme.CoffeeTextMain
import com.example.coffeemanager.ui.theme.CoffeeTextSecondary
import com.example.coffeemanager.viewmodel.HomeViewModel


@Composable
fun Home(viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var selectedFeeling by remember { mutableStateOf("Energized") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home Icon",
                modifier = Modifier.padding(start = 169.dp),
                tint = CoffeeTextMain
            )

            Text(text = "Home",
                modifier = Modifier.padding(start = 8.dp),
                color = CoffeeTextMain,
                fontSize = 24.sp
            )
        }

        Text("Welcome, Guest", textAlign = TextAlign.Center, color = CoffeeTextMain, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DashboardCard(
                title = "LAST CUP", 
                value = uiState.lastCupTime, 
                subValue = "Today's Caffeine:\n${uiState.todaysCaffeine} mg", 
                modifier = Modifier.weight(1f)
            )
            EnergyStatusCard(
                energyLevel = uiState.energyLevel,
                statusText = uiState.energyStatus,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeSurface),
                border = BorderStroke(1.dp, Border)
            ) {
                Column {
                    Text("PRESENCE", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), textAlign = TextAlign.Center, color = CoffeeTextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("HOW ARE YOU FEELING?", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), textAlign = TextAlign.Center, color = CoffeeTextMain, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { selectedFeeling = "Calm" },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedFeeling == "Calm") CoffeePrimaryAccent else CoffeeSurface,
                                contentColor = if (selectedFeeling == "Calm") CoffeeBackground else CoffeeTextMain
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("😊", fontSize = 20.sp)
                                Text("Calm", fontSize = 12.sp, maxLines = 1)
                            }
                        }
                        Button(
                            onClick = { selectedFeeling = "Energized" },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedFeeling == "Energized") CoffeePrimaryAccent else CoffeeSurface,
                                contentColor = if (selectedFeeling == "Energized") CoffeeBackground else CoffeeTextMain
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(4.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("😁", fontSize = 20.sp)
                                Text("Energized", fontSize = 12.sp, maxLines = 1)
                            }
                        }
                    }
                }
            }
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeSurface),
                border = BorderStroke(1.dp, Border)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("WEEKLY INTAKE", color = CoffeeTextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    if (uiState.weeklyIntake.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Nothing to show",
                                color = CoffeeTextSecondary,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        WeeklyIntakeChart(weeklyData = uiState.weeklyIntake, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = Border, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        LogCoffeeCard(
            onLog = { bean, shotCount ->
                viewModel.logCoffee(bean, shotCount, selectedFeeling)
                Toast.makeText(context, "Coffee logged successfully!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun EnergyStatusCard(energyLevel: Float, statusText: String, modifier: Modifier = Modifier) {
    CoffeeCard(modifier = modifier.height(200.dp)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "CURRENT CAFFEINE",
                color = CoffeeTextMain,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                "STATUS:",
                color = CoffeeTextMain,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                statusText,
                color = CoffeePrimaryAccent,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "⚡",
                fontSize = 24.sp,
                color = CoffeePrimaryAccent,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Energy Bar below lightning bolt
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Centered bar with some margin
                    .height(8.dp)
                    .background(CoffeeBackground, RoundedCornerShape(4.dp))
                    .border(BorderStroke(1.dp, Border), RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(energyLevel)
                        .fillMaxHeight()
                        .background(CoffeePrimaryAccent, RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, value: String, subValue: String, modifier: Modifier = Modifier, isAccent: Boolean = false) {
    CoffeeCard(modifier = modifier.height(200.dp)) {
        Column(
            modifier = Modifier
                .padding(22.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, color = CoffeeTextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            Text(value, color = if (isAccent) CoffeePrimaryAccent else CoffeeTextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            Text(subValue, color = CoffeeTextMain, fontSize = 15.sp, lineHeight = 16.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CoffeeCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeSurface),
        border = BorderStroke(1.dp, Border),
        content = { content() }
    )
}

@Composable
fun WeeklyIntakeChart(weeklyData: List<Float>, modifier: Modifier = Modifier) {
    val days = listOf("M", "T", "W", "T", "F", "S", "S")

    Column(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val y = size.height * 0.3f
                drawLine(
                    color = CoffeeTextMain.copy(alpha = 0.3f),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    strokeWidth = 2f
                )
            }
            Text("Goal", color = CoffeeTextMain.copy(alpha = 0.5f), fontSize = 10.sp, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 10.dp))

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                weeklyData.forEach { heightFactor ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight((heightFactor * 0.7f).coerceIn(0f, 1f)) // 1.0 matches goal line at 70% height
                            .background(
                                CoffeePrimaryAccent,
                                RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                            )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            days.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = CoffeeTextMain,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun LogCoffeeCard(onLog: (String, Int) -> Unit) {
    var selectedBean by remember { mutableStateOf("Espresso Arabica") }
    var shots by remember { mutableIntStateOf(2) }
    var isExpanded by remember { mutableStateOf(false) }
    val options = listOf("Espresso Arabica", "Americano", "Robusta")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeSurface),
        border = BorderStroke(1.dp, Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("☕", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text("LOG COFFEE", color = CoffeeTextMain, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("SELECT BEAN TYPE:", color = CoffeeTextMain, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                
                // Selector
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CoffeeBackground)
                        .border(BorderStroke(1.dp, Border), RoundedCornerShape(8.dp))
                        .clickable { isExpanded = !isExpanded }
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(selectedBean, color = CoffeeTextMain, fontSize = 14.sp)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = CoffeeTextMain)
                    }
                }

                if (isExpanded) {
                    Spacer(Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(CoffeeBackground)
                            .border(BorderStroke(1.dp, Border), RoundedCornerShape(8.dp))
                    ) {
                        options.forEach { option ->
                            Text(
                                text = option,
                                color = if (option == selectedBean) CoffeePrimaryAccent else CoffeeTextSecondary,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedBean = option
                                        isExpanded = false
                                    }
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("NUM. SHOTS", color = CoffeeTextMain, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { if (shots > 1) shots-- },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CoffeeBackground)
                            .border(BorderStroke(1.dp, Border), RoundedCornerShape(8.dp))
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = null, tint = CoffeeTextMain, modifier = Modifier.size(16.dp))
                    }
                    
                    Text(
                        shots.toString(),
                        color = CoffeeTextMain,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(
                        onClick = { shots++ },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CoffeeBackground)
                            .border(BorderStroke(1.dp, Border), RoundedCornerShape(8.dp))
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = CoffeeTextMain, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onLog(selectedBean, shots) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoffeePrimaryAccent)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("LOG COFFEE", color = CoffeeBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.width(8.dp))
                    Text("☕", fontSize = 16.sp)
                }
            }
        }
    }
}