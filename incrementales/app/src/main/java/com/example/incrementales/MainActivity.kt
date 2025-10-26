package com.example.incrementales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFE4E9)
                ) {
                    ButtonGrid()
                }
            }
        }
    }
}

@Composable
fun ButtonGrid() {
    val counters = remember { mutableStateListOf(*Array(12) { 0 }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (row in 0 until 4) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    Button(
                        onClick = { counters[index]++ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF80AB)
                        ),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .size(90.dp)
                    ) {
                        Text(
                            text = counters[index].toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White 
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}